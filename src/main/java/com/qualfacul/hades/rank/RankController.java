package com.qualfacul.hades.rank;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qualfacul.hades.annotation.Get;
import com.qualfacul.hades.annotation.PublicEndpoint;
import com.qualfacul.hades.college.rank.CollegeRank;
import com.qualfacul.hades.college.rank.CollegeRankDTO;
import com.qualfacul.hades.college.rank.CollegeRankRepository;
import com.qualfacul.hades.college.rank.CollegeRankToDTOConverter;
import com.qualfacul.hades.college.rank.CollegeRankType;
import com.qualfacul.hades.search.PaginatedResult;

@RestController
public class RankController {

	private static final int MAX_RANK_RESULT_PER_PAGE = 50;
	
	@Autowired
	private CollegeRankRepository  collegeRankRepository;
	@Autowired
	private CollegeRankToDTOConverter collegeRankConverter;
	
	@PublicEndpoint
	@Get("/rank/colleges/{rankType}")
	public PaginatedResult<CollegeRankDTO> listCollegeRank(@PathVariable CollegeRankType rankType,
			@RequestParam(required = false) Integer page) {
		
		if(page == null || page < 1){
			page = 1;
		}
		PageRequest pageRequest = new PageRequest(page-1, MAX_RANK_RESULT_PER_PAGE);
		Page<CollegeRank> collegeRankPage = collegeRankRepository
				.findByRankTypeAndGradesQuantityGreaterThanOrderByGradeDesc(rankType, 5, pageRequest);

		List<CollegeRankDTO> dtos = new ArrayList<>();
		
		int position = (page * MAX_RANK_RESULT_PER_PAGE) - MAX_RANK_RESULT_PER_PAGE + 1;
		for (CollegeRank collegeRank : collegeRankPage.getContent()) {
			CollegeRankDTO dto = collegeRankConverter.convert(collegeRank);
			dto.setPosition(position++);
			dtos.add(dto);
		}
		
		PaginatedResult<CollegeRankDTO> paginatedSearch = new PaginatedResult<CollegeRankDTO>(dtos, page,
				(int) collegeRankPage.getTotalElements(), MAX_RANK_RESULT_PER_PAGE);
		return paginatedSearch;
	}
}
