/*
 * Copyright 2016-2016 the original author or authors.
 *
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
 */
package net.biville.florent.sproccompiler.testutils;

import com.google.testing.compile.JavaFileObjects;
import net.biville.florent.sproccompiler.StoredProcedureProcessorTest;

import javax.tools.JavaFileObject;
import java.net.URL;

public class JavaFileObjectUtils {

    public static JavaFileObject resource(String url) {
        return JavaFileObjects.forResource(at(url));
    }

    private static URL at(String resource) {
        return StoredProcedureProcessorTest.class.getResource(String.format("/test_classes/%s", resource));
    }
}
