package dev.igorsyrbu.youtubevideoupdater.youtube.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VideoStatistics {

	@JsonProperty("viewCount")
	private String viewCount;

	@JsonProperty("likeCount")
	private String likeCount;

	@JsonProperty("dislikeCount")
	private String dislikeCount;

	@JsonProperty("favoriteCount")
	private String favoriteCount;

	@JsonProperty("commentCount")
	private String commentCount;
}