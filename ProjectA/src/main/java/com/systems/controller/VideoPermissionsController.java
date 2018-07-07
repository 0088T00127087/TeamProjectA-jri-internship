package com.systems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.systems.repository.VideoPermissionsRepository;

@RestController
@RequestMapping("/video")
public class VideoPermissionsController {

	
	@Autowired
	VideoPermissionsRepository repo;
	
	@GetMapping("/valid/{videoNumber}")
	public String videoWatchableValid(@PathVariable("videoNumber") String videoNumber){
		try {
			int videoWatched = repo.isVideoWatched(videoNumber);
			int videoRequiresApproval = repo.isVideoRequiringManagerApproval(videoNumber);
			
			if (videoRequiresApproval == 1) {
				return "approvalRequired";
			} else if (videoWatched == 1) {
				return "videoWatched";
			} else {
				return "valid";
			}			
		} catch (Exception ex) {
			return "unknownError";
		}
	}
	
	@PostMapping("/watched/{videoNumber}")
	public String adjustVideoWatched(@PathVariable("videoNumber") String videoNumber){
		try {
			repo.updateVideoWatched(videoNumber);
			return "success";
		} catch (Exception ex) {
			return "failedToUpdateTable";
		}
	}
	
	@PostMapping("/assign/{videoNumber}")
	public String managerReassignsVideo(@PathVariable("videoNumber") String videoNumber) {
		try {
			repo.reassignVideo(videoNumber);
			return "success";
		} catch (Exception ex) {
			System.out.println(ex);
			return "failedToUpdateTable";
		}
	}
}
