package dev.igorsyrbu.youtubevideoupdater.youtube.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class YoutubeVideo {

	@JsonProperty("id")
	private String id;

	@JsonProperty("snippet")
	private VideoSnippet snippet;

	@JsonProperty("statistics")
	private VideoStatistics statistics;
}