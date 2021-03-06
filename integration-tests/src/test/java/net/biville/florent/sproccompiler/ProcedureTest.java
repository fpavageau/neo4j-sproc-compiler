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
package net.biville.florent.sproccompiler;

import org.junit.Rule;
import org.junit.Test;
import test_classes.working_procedures.Procedures;

import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.harness.junit.Neo4jRule;

import static org.assertj.core.api.Assertions.assertThat;


public class ProcedureTest
{

    @Rule
    public Neo4jRule graphDb = new Neo4jRule().withProcedure( Procedures.class );


    @Test
    public void calls_simplistic_procedure()
    {
        try ( Driver driver = GraphDatabase.driver( graphDb.boltURI(), configuration() );
                Session session = driver.session() )
        {

            StatementResult result = session.run( "CALL test_classes.working_procedures.theAnswer()" );

            assertThat( result.single().get( "value" ).asLong() ).isEqualTo( 42L );
        }
    }

    @Test
    public void calls_procedures_with_simple_input_type_returning_void()
    {
        try ( Driver driver = GraphDatabase.driver( graphDb.boltURI(), configuration() );
                Session session = driver.session() )
        {

            session.run( "CALL test_classes.working_procedures.simpleInput00()" );
            session.run( "CALL test_classes.working_procedures.simpleInput01('string')" );
            session.run( "CALL test_classes.working_procedures.simpleInput02(42)" );
            session.run( "CALL test_classes.working_procedures.simpleInput03(42)" );
            session.run( "CALL test_classes.working_procedures.simpleInput04(4.2)" );
            session.run( "CALL test_classes.working_procedures.simpleInput05(true)" );
            session.run( "CALL test_classes.working_procedures.simpleInput06(false)" );
            session.run( "CALL test_classes.working_procedures.simpleInput07({foo:'bar'})" );
            session.run( "MATCH (n)            CALL test_classes.working_procedures.simpleInput08(n) RETURN n" );
            session.run( "MATCH p=(()-[r]->()) CALL test_classes.working_procedures.simpleInput09(p) RETURN p" );
            session.run( "MATCH ()-[r]->()     CALL test_classes.working_procedures.simpleInput10(r) RETURN r" );
        }
    }

    @Test
    public void calls_procedures_with_simple_input_type_returning_record_with_primitive_fields()
    {
        try ( Driver driver = GraphDatabase.driver( graphDb.boltURI(), configuration() );
                Session session = driver.session() )
        {

            assertThat( session.run( "CALL test_classes.working_procedures.simpleInput11('string')" ).single() )
                    .isNotNull();
            assertThat( session.run( "CALL test_classes.working_procedures.simpleInput12(42)" ).single() ).isNotNull();
            assertThat( session.run( "CALL test_classes.working_procedures.simpleInput13(42)" ).single() ).isNotNull();
            assertThat( session.run( "CALL test_classes.working_procedures.simpleInput14(4.2)" ).single() ).isNotNull();
            assertThat( session.run( "CALL test_classes.working_procedures.simpleInput15(true)" ).single() )
                    .isNotNull();
            assertThat( session.run( "CALL test_classes.working_procedures.simpleInput16(false)" ).single() )
                    .isNotNull();
            assertThat( session.run( "CALL test_classes.working_procedures.simpleInput17({foo:'bar'})" ).single() )
                    .isNotNull();
//            assertThat(session.run("MATCH (n)            CALL test_classes.working_procedures.simpleInput18(n) YIELD field01 RETURN *").single()).isNotNull();
//            assertThat(session.run("MATCH p=(()-[r]->()) CALL test_classes.working_procedures.simpleInput19(p) YIELD field01 RETURN *").single()).isNotNull();
//            assertThat(session.run("MATCH ()-[r]->()     CALL test_classes.working_procedures.simpleInput20(r) YIELD field01 RETURN *").single()).isNotNull();
            assertThat( session.run( "CALL test_classes.working_procedures.simpleInput21()" ).single() ).isNotNull();
        }

    }

    private Config configuration()
    {
        return Config.build().withEncryptionLevel( Config.EncryptionLevel.NONE ).toConfig();
    }

}
