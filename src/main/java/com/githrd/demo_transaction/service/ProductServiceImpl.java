package com.githrd.demo_transaction.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.githrd.demo_transaction.dao.ProductInMapper;
import com.githrd.demo_transaction.dao.ProductOutMapper;
import com.githrd.demo_transaction.dao.ProductRemainMapper;
import com.githrd.demo_transaction.vo.ProductVo;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductInMapper productInMapper;			//입고
	@Autowired
	ProductOutMapper productOutMapper;			//출고
	@Autowired
	ProductRemainMapper productRemainMapper;	//재고

	@Override
	public Map<String, List<ProductVo>> selectTotalMap() {
		
		List<ProductVo> in_list 	= productInMapper.selectList();		// 입고목록
		List<ProductVo> out_list 	= productOutMapper.selectList();		// 출고목록
		List<ProductVo> remain_list = productRemainMapper.selectList();	// 재고목록
		
		Map<String, List<ProductVo>> map = new HashMap<String, List<ProductVo>>();
		
		map.put("in_list", in_list);
		map.put("out_list", out_list);
		map.put("remain_list", remain_list);
		
		return map;
	}

	@Override
	public int insert_in(ProductVo vo) throws Exception {
		int res = 0;
		
		// 1.입고등록하기
		res = productInMapper.insert(vo);
		
		// 2.재고등록(수정)처리
		ProductVo remainVo = productRemainMapper.selectOneFromName(vo.getName());
		
		if(remainVo==null) {
			//등록추가(등록상품이 없기때문)
			res = productRemainMapper.insert(vo);
		} else {
			//상품기등록상태: 수량 수정
			// 재고수량 = 기존재고수량 + 추가수량
			int cnt = remainVo.getCnt() + vo.getCnt();
			remainVo.setCnt(cnt);
			
			res = productRemainMapper.update(remainVo);
		}
		
		return res;
	}

	@Override
	public int insert_out(ProductVo vo) throws Exception {
		int res = 0;
		
		//1.출고등록
		res = productOutMapper.insert(vo);
		
		//2.재고정보 얻어오기
		ProductVo remainVo = productRemainMapper.selectOneFromName(vo.getName());
		
		if(remainVo==null) {
			
			// 재고목록에 상품이 없을경우
			throw new Exception("remain_not");	// res = productOutMapper.insert(vo); 이 작업이 취소됨
			
		} else {
			//재고수량 =    원래재고수량   -  출고수량
			int cnt    = remainVo.getCnt() - vo.getCnt();
			
			if(cnt < 0) {
				//재고수량이 부족한 경우
				throw new Exception("remain_lack");
			}
			
			//재고수량 수정
			remainVo.setCnt(cnt);
			res = productRemainMapper.update(remainVo);
		}
		return res;
	}

	@Override
	public int delete_in(int idx) throws Exception {
		int res = 0;
		
		//0.취소할 입고상품정보 얻어오기
		ProductVo vo 	= productInMapper.selectOne(idx);
		
		//1.입고삭제
		res = productInMapper.delete(idx);
		
		//2.재고상품 수정
		ProductVo remainVo = productRemainMapper.selectOneFromName(vo.getName());
		
		int cnt    = remainVo.getCnt() - vo.getCnt();
		
		if(cnt < 0) {
			throw new Exception("delete_in_lack");
		}
		
		//재고수정
		remainVo.setCnt(cnt);
		res = productRemainMapper.update(remainVo);
		
		return res;
	}

	@Override
	public int delete_out(int idx) throws Exception {
		int res = 0;
		
		//0.출고취소할 상품정보 얻어오기
		ProductVo vo 	= productOutMapper.selectOne(idx);
		
		//1.출고삭제
		res = productOutMapper.delete(idx);
		
		//2.재고상품 수정
		ProductVo remainVo = productRemainMapper.selectOneFromName(vo.getName());
		
		int cnt    = remainVo.getCnt() + vo.getCnt();
		
		if(cnt < 0) {
			throw new Exception("delete_out_lack");
		}
		
		//재고수정
		remainVo.setCnt(cnt);
		res = productRemainMapper.update(remainVo);
		
		return res;
	}

	

}