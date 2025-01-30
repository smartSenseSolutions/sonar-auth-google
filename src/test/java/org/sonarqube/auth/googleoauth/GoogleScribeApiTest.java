/*
 * Google Authentication for SonarQube
 * Copyright (C) 2016-2016 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonarqube.auth.googleoauth;

/*-
 * #%L
 * Google Authentication for SonarQube
 * %%
 * Copyright (C) 2016 SonarSource
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.github.scribejava.core.extractors.JsonTokenExtractor;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.model.Verb;
import org.junit.Test;
import org.sonar.api.config.Settings;

import static org.assertj.core.api.Assertions.assertThat;
import org.sonar.api.config.internal.MapSettings;

public class GoogleScribeApiTest {

  GoogleScribeApi underTest = new GoogleScribeApi(new GoogleSettings(new MapSettings()));

  @Test
  public void getAccessTokenEndpoint() {
    assertThat(underTest.getAccessTokenEndpoint()).isEqualTo("https://www.googleapis.com/oauth2/v3/token");
  }

  @Test
  public void getAccessTokenVerb() {
    assertThat(underTest.getAccessTokenVerb()).isEqualTo(Verb.POST);
  }

  @Test
  public void getAuthorizationUrl() {
    OAuthConfig oAuthConfig = new OAuthConfig("key", null, "callback", null, "the-scope", null, null, null, null);
    assertThat(underTest.getAuthorizationUrl(oAuthConfig)).isEqualTo(
            "https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=key&redirect_uri=callback&scope=the-scope"
      );

    oAuthConfig = new OAuthConfig("key", null, "callback", null, "the-scope", null, null, null, null);
    oAuthConfig.setState("my-test-state");
    assertThat(underTest.getAuthorizationUrl(oAuthConfig)).isEqualTo(
            "https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=key&redirect_uri=callback&scope=the-scope&state=my-test-state"
      );
  }

  @Test
  public void getAccessTokenExtractor() {
    assertThat(underTest.getAccessTokenExtractor()).isInstanceOf(JsonTokenExtractor.class);
  }
}
