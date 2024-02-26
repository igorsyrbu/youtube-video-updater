package dev.igorsyrbu.youtubevideoupdater.youtube.content;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "youtube-content", url = "https://www.googleapis.com")
public interface YoutubeContentFeignClient {

	String IMAGE_PNG = "image/png";
	String RESUMABLE = "resumable";

	@GetMapping("/youtube/v3/videos")
	YoutubeVideos getVideosInfo(@RequestParam("id") String videoId,
								@RequestParam("part") String part,
								@RequestHeader("Authorization") String token);

	@PutMapping("/youtube/v3/videos")
	YoutubeVideo updateVideo(@RequestParam("part") String part,
							 @RequestHeader("Authorization") String token,
							 @RequestBody YoutubeVideo video);

	@PostMapping(value = "/upload/youtube/v3/thumbnails/set", consumes = IMAGE_PNG)
	ResponseEntity<String> uploadThumbnail(@RequestHeader(value = "Authorization") String token,
										   @RequestParam(value = "uploadType") String uploadType,
										   @RequestParam(value = "videoId") String videoId,
										   @RequestParam(value = "upload_id", required = false) String uploadId,
										   @RequestBody(required = false) byte[] content);

	default void uploadThumbnail(String token,
								 String videoId,
								 String uploadId,
								 byte[] content) {
		uploadThumbnail(token, RESUMABLE, videoId, uploadId, content);
	}

	default ResponseEntity<String> getThumbnailUploadUrl(String videoId, String token) {
		return uploadThumbnail(token, RESUMABLE, videoId, null, new byte[0]);
	}
}
