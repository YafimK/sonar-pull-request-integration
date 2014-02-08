package com.contaazul.sonarpullrequestintegration.mojo;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.apache.maven.model.Build;
import org.apache.maven.project.MavenProject;
import org.eclipse.egit.github.core.CommitFile;
import org.junit.Test;

import com.google.common.collect.Lists;

public class ComponentConverterTest {

	@Test
	public void toComponentKey() {
		ComponentConverter c = new ComponentConverter("hazelcast", projects(), commits() );
		assertEquals(
				"com.mysema.querydsl:querydsl-hazelcast:hazelcast:com.mysema.query.hazelcast.AbstractIMapQuery",
				c.pathToComponent( "/querydsl-hazelcast/src/main/java/com/mysema/query/hazelcast/AbstractIMapQuery.java" ) );
		assertEquals(
				"com.mysema.querydsl:querydsl-hazelcast:hazelcast:com.mysema.query.hazelcast.impl.HazelcastSerializer",
				c.pathToComponent( "/querydsl-hazelcast/src/main/java/com/mysema/query/hazelcast/impl/HazelcastSerializer.java" ) );
		assertEquals(
				null,
				c.pathToComponent( "/querydsl-hazelcast/src/test/java/com/mysema/query/hazelcast/impl/HazelcastSerializerTest.java" ) );
	}

	private List<CommitFile> commits() {
		List<CommitFile> commits = Lists.newArrayList();
		commits.add( new CommitFile()
				.setFilename( "/querydsl-hazelcast/src/main/java/com/mysema/query/hazelcast/AbstractIMapQuery.java" ) );
		commits.add( new CommitFile()
				.setFilename( "/querydsl-hazelcast/src/main/java/com/mysema/query/hazelcast/impl/HazelcastSerializer.java" ) );
		return commits;
	}

	private List<MavenProject> projects() {
		List<MavenProject> projects = Lists.newArrayList();
		MavenProject root = new MavenProject();
		root.setGroupId( "com.mysema.querydsl" );
		root.setArtifactId( "querydsl-root" );
		root.setFile( new File(
				"src/test/resources/querydsl/querydsl-root/pom.xml" ) );
		projects.add( root );
		MavenProject hazelcast = new MavenProject();
		hazelcast.setGroupId( "com.mysema.querydsl" );
		hazelcast.setArtifactId( "querydsl-hazelcast" );
		hazelcast.setFile( new File(
				"src/test/resources/querydsl/querydsl-hazelcast/pom.xml" ) );
		hazelcast.setBuild( new Build() );
		hazelcast.getBuild().setSourceDirectory( new File(
				"src/test/resources/querydsl/querydsl-hazelcast/src/main/java" ).getAbsolutePath() );
		projects.add( hazelcast );

		return projects;
	}

}
