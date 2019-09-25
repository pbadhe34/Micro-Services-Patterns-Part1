package com.user.tagging;

import java.util.ArrayList;
import java.util.List;

 

public class RunTagPostClient {

	public static void main(String[] args) {
		TagRepository repository = new TagRepository();

		System.out.println("Posting new Tags");
		List<String> tagList = new ArrayList<String>();
		tagList.add("Tag1");
		tagList.add("MyTag");
		tagList.add("TestTag");
		repository.addTags("MyBookTags", tagList);
		
		List<Post> results = repository.postsWithAtLeastOneTag("MyBookTags");
		results.forEach(System.out::println);

		 
		results.forEach(post -> {
			System.out.println(post.getTags());
			//post.getTags().contains("MongoDB"));
		});

	}

}
