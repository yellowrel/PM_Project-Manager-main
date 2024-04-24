package com.kosa.tikitaka.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kosa.tikitaka.model.DocPageDTO;
import com.kosa.tikitaka.service.DocService;

@Controller
@RequestMapping("/")
public class DocController {

	@Autowired
	private DocService docService;

	// 문서 리스트
	@RequestMapping("/document")
	public String document(Model model) {
		List<DocPageDTO> docList = docService.getAllDocs();
		model.addAttribute("docList", docList); // docList 이름으로 모델에 데이터를 추가
		return "document";
	}

	// 문서 상세보기 페이지
	@GetMapping("/document/{docPageNo}")
	@ResponseBody
	public DocPageDTO showDocDetails(@PathVariable int docPageNo) {
		System.out.println(docPageNo);
		DocPageDTO doc = docService.getDocByNO(docPageNo);

		return doc;
	}

	// 문서 등록 페이지 이동
	@GetMapping("/document/add")
	public String showAddDocPage() {
		return "docAddPage";
	}

	// 문서 등록 처리
	@RequestMapping(value = "/document/add", method = RequestMethod.POST)
	@ResponseBody
	public HashMap addDoc(@RequestBody DocPageDTO docDTO) throws Exception {

		docService.addDoc(docDTO);
		System.out.println("데이터: " + docDTO);

		HashMap<String, Object> map = new HashMap<>();
		map.put("status", "success");
		map.put("list", docService.getAllDocs());
		System.out.println("리스트:" + docService.getAllDocs());
		return map;

	}

	// 수정 페이지 이동
	@GetMapping("/document/edit/{docPageNo}")
	@ResponseBody
	public ModelAndView editDocPage(@PathVariable("docPageNo") int docPageNo, Model model) {
		DocPageDTO doc = docService.getDocByNO(docPageNo);
		model.addAttribute("doc", doc); // 가져온 문서를 모델에 추가하여 뷰에 전달
		ModelAndView view = new ModelAndView("docEditPage");
		return view;

	}

	// 문서 수정 처리
	@RequestMapping(value = "/document/edit", method = RequestMethod.POST)
	@ResponseBody
	public HashMap updateDoc(@RequestBody DocPageDTO docDTO) throws Exception {
		System.out.println("데이터: " + docDTO);

		docService.updateDoc(docDTO);
		HashMap map = new HashMap<>();
		map.put("status", "success");
		map.put("list", docService.getAllDocs());
		System.out.println("list: " + docService.getAllDocs());
		return map;
	}

	// 삭제
	@RequestMapping(value = "/document/delete/{docPageNo}", method = RequestMethod.DELETE)
	@ResponseBody
	public HashMap deleteDoc(@PathVariable("docPageNo") int docPageNo) {
		docService.deleteDoc(docPageNo);
		HashMap map = new HashMap<>();
		map.put("status", "success");
		return map;
	}
}
