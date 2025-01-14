/*
 * Copyright 2017-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.spring.javaformat.gradle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.spring.javaformat.gradle.tasks.Format;
import io.spring.javaformat.gradle.testkit.GradleBuild;
import io.spring.javaformat.gradle.testkit.GradleBuildExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Format}.
 *
 * @author Phillip Webb
 */
@ExtendWith(GradleBuildExtension.class)
public class FormatTaskTests {

	public GradleBuild gradleBuild;

	@Test
	public void checkOk() throws IOException {
		BuildResult result = this.gradleBuild.source("src/test/resources/format").build("format");
		assertThat(result.task(":formatMain").getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
		File formattedFile = new File(this.gradleBuild.getProjectDir(), "src/main/java/simple/Simple.java");
		String formattedContent = new String(Files.readAllBytes(formattedFile.toPath()));
		assertThat(formattedContent).contains("class Simple {").contains("	public static void main");
	}

	@Test
	public void checkSpacesOk() throws IOException {
		BuildResult result = this.gradleBuild.source("src/test/resources/format-spaces").build("format");
		assertThat(result.task(":formatMain").getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
		File formattedFile = new File(this.gradleBuild.getProjectDir(), "src/main/java/simple/Simple.java");
		String formattedContent = new String(Files.readAllBytes(formattedFile.toPath()));
		assertThat(formattedContent).contains("class Simple {").contains("    public static void main");
	}

}
