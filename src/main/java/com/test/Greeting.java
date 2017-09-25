package com.test;

import org.codehaus.jackson.annotate.JsonProperty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @JsonProperty - In order for you to directly bind your data to your custom types, you need to specify the variable name exact same as the key in the JSON Document returned from the API. 
 * In case your variable name and key in JSON doc are not matching, you need to use @JsonProperty annotation to specify the exact key of JSON document.
 * 
 * Jackson has two different annotations to use when you want to exclude some class members from the JSON serialization and deserialization processes. These two annotations are @JsonIgnore and @JsonIgnoreProperties. 
@JsonIgnoreProperties is an annotation at the class level and it expects that the properties to be excluded would be explicitly indicated in the form of a list of strings.
@JsonIgnore instead is a member-level or method-level annotation, which expects that the properties to be excluded are marked one by one. To completely exclude a member from the process of serialization and de-serialization it’s possibile to annotate the actual property or its setter or its getter.
 * ALL ABOVE ARE SPECIFIC TO JACKSON JSON.
 */

//@JsonIgnoreProperties({"content"})
public class Greeting {
	 @JsonProperty("abc")
	 //@JsonIgnore
	 private long id;
	 @JsonProperty("xyz")
	 private String content;
	
	//default constructor is MUST for @RequestBody annotation used in web service
	 public Greeting() {
		super();
	}

	public Greeting(long id, String content) {
	     this.id = id;
	     this.content = content;
	 }

	 public long getId() {
	     return id;
	 }

	 public String getContent() {
	     return content;
	 }

	 @Override
	 public String toString() {
		return "Greeting [id=" + id + ", content=" + content + "]";
	 }
}
