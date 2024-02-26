package dev.igorsyrbu.youtubevideoupdater.youtube.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class VideoSnippet {

	@JsonProperty("publishedAt")
	private String publishedAt;

	@JsonProperty("channelId")
	private String channelId;

	@JsonProperty("title")
	private String title;

	@JsonProperty("description")
	private String description;

	@JsonProperty("channelTitle")
	private String channelTitle;

	@JsonProperty("tags")
	private List<String> tags;

	@JsonProperty("categoryId")
	private String categoryId;
}