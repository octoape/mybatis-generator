/*
 *    Copyright 2006-2025 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package mbg.test.mb3.hierarchical.immutable;

import static mbg.test.common.util.TestUtilities.blobsAreEqual;
import static mbg.test.common.util.TestUtilities.generateRandomBlob;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import mbg.test.mb3.generated.hierarchical.immutable.mapper.FieldsblobsMapper;
import mbg.test.mb3.generated.hierarchical.immutable.mapper.FieldsonlyMapper;
import mbg.test.mb3.generated.hierarchical.immutable.mapper.PkblobsMapper;
import mbg.test.mb3.generated.hierarchical.immutable.mapper.PkfieldsMapper;
import mbg.test.mb3.generated.hierarchical.immutable.mapper.PkfieldsblobsMapper;
import mbg.test.mb3.generated.hierarchical.immutable.mapper.PkonlyMapper;
import mbg.test.mb3.generated.hierarchical.immutable.model.Fieldsblobs;
import mbg.test.mb3.generated.hierarchical.immutable.model.FieldsblobsExample;
import mbg.test.mb3.generated.hierarchical.immutable.model.FieldsblobsWithBLOBs;
import mbg.test.mb3.generated.hierarchical.immutable.model.Fieldsonly;
import mbg.test.mb3.generated.hierarchical.immutable.model.FieldsonlyExample;
import mbg.test.mb3.generated.hierarchical.immutable.model.PkblobsExample;
import mbg.test.mb3.generated.hierarchical.immutable.model.PkblobsKey;
import mbg.test.mb3.generated.hierarchical.immutable.model.PkblobsWithBLOBs;
import mbg.test.mb3.generated.hierarchical.immutable.model.Pkfields;
import mbg.test.mb3.generated.hierarchical.immutable.model.PkfieldsExample;
import mbg.test.mb3.generated.hierarchical.immutable.model.Pkfieldsblobs;
import mbg.test.mb3.generated.hierarchical.immutable.model.PkfieldsblobsExample;
import mbg.test.mb3.generated.hierarchical.immutable.model.PkfieldsblobsWithBLOBs;
import mbg.test.mb3.generated.hierarchical.immutable.model.PkonlyExample;
import mbg.test.mb3.generated.hierarchical.immutable.model.PkonlyKey;

/**
 * @author Jeff Butler
 */
public class UpdateByExampleTest extends AbstractHierarchicalImmutableTest {

    @Test
    public void testFieldsOnlyUpdateByExampleSelective() {

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            FieldsonlyMapper mapper = sqlSession
                    .getMapper(FieldsonlyMapper.class);
            Fieldsonly record = new Fieldsonly(5, 11.22, 33.44);
            mapper.insert(record);

            record = new Fieldsonly(8, 44.55, 66.77);
            mapper.insert(record);

            record = new Fieldsonly(9, 88.99, 100.111);
            mapper.insert(record);

            record = new Fieldsonly(null, 99d, null);
            FieldsonlyExample example = new FieldsonlyExample();
            example.createCriteria().andIntegerfieldGreaterThan(5);

            int rows = mapper.updateByExampleSelective(record, example);
            assertEquals(2, rows);

            example.clear();
            example.createCriteria().andIntegerfieldEqualTo(5);
            List<Fieldsonly> answer = mapper.selectByExample(example);
            assertEquals(1, answer.size());
            record = answer.get(0);
            assertEquals(11.22, record.getDoublefield(), 0.001);
            assertEquals(33.44, record.getFloatfield(), 0.001);
            assertEquals(5, record.getIntegerfield().intValue());

            example.clear();
            example.createCriteria().andIntegerfieldEqualTo(8);
            answer = mapper.selectByExample(example);
            assertEquals(1, answer.size());
            record = answer.get(0);
            assertEquals(99d, record.getDoublefield(), 0.001);
            assertEquals(66.77, record.getFloatfield(), 0.001);
            assertEquals(8, record.getIntegerfield().intValue());

            example.clear();
            example.createCriteria().andIntegerfieldEqualTo(9);
            answer = mapper.selectByExample(example);
            assertEquals(1, answer.size());
            record = answer.get(0);
            assertEquals(99d, record.getDoublefield(), 0.001);
            assertEquals(100.111, record.getFloatfield(), 0.001);
            assertEquals(9, record.getIntegerfield().intValue());
        }
    }

    @Test
    public void testFieldsOnlyUpdateByExample() {

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            FieldsonlyMapper mapper = sqlSession
                    .getMapper(FieldsonlyMapper.class);
            Fieldsonly record = new Fieldsonly(5, 11.22, 33.44);
            mapper.insert(record);

            record = new Fieldsonly(8, 44.55, 66.77);
            mapper.insert(record);

            record = new Fieldsonly(9, 88.99, 100.111);
            mapper.insert(record);

            record = new Fieldsonly(22, null, null);
            FieldsonlyExample example = new FieldsonlyExample();
            example.createCriteria().andIntegerfieldEqualTo(5);

            int rows = mapper.updateByExample(record, example);
            assertEquals(1, rows);

            example.clear();
            example.createCriteria().andIntegerfieldEqualTo(22);
            List<Fieldsonly> answer = mapper.selectByExample(example);
            assertEquals(1, answer.size());
            record = answer.get(0);
            assertNull(record.getDoublefield());
            assertNull(record.getFloatfield());
            assertEquals(22, record.getIntegerfield().intValue());
        }
    }

    @Test
    public void testPKOnlyUpdateByExampleSelective() {

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            PkonlyMapper mapper = sqlSession.getMapper(PkonlyMapper.class);
            PkonlyKey key = new PkonlyKey(1, 3);
            mapper.insert(key);

            key = new PkonlyKey(5, 6);
            mapper.insert(key);

            key = new PkonlyKey(7, 8);
            mapper.insert(key);

            PkonlyExample example = new PkonlyExample();
            example.createCriteria().andIdGreaterThan(4);
            key = new PkonlyKey(null, 3);
            int rows = mapper.updateByExampleSelective(key, example);
            assertEquals(2, rows);

            example.clear();
            example.createCriteria().andIdEqualTo(5).andSeqNumEqualTo(3);

            long returnedRows = mapper.countByExample(example);
            assertEquals(1, returnedRows);

            example.clear();
            example.createCriteria().andIdEqualTo(7).andSeqNumEqualTo(3);

            returnedRows = mapper.countByExample(example);
            assertEquals(1, returnedRows);
        }
    }

    @Test
    public void testPKOnlyUpdateByExample() {

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            PkonlyMapper mapper = sqlSession.getMapper(PkonlyMapper.class);
            PkonlyKey key = new PkonlyKey(1, 3);
            mapper.insert(key);

            key = new PkonlyKey(5, 6);
            mapper.insert(key);

            key = new PkonlyKey(7, 8);
            mapper.insert(key);

            PkonlyExample example = new PkonlyExample();
            example.createCriteria().andIdEqualTo(7);
            key = new PkonlyKey(22, 3);
            int rows = mapper.updateByExample(key, example);
            assertEquals(1, rows);

            example.clear();
            example.createCriteria().andIdEqualTo(22).andSeqNumEqualTo(3);

            long returnedRows = mapper.countByExample(example);
            assertEquals(1, returnedRows);
        }
    }

    @Test
    public void testPKFieldsUpdateByExampleSelective() {

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            PkfieldsMapper mapper = sqlSession.getMapper(PkfieldsMapper.class);
            Pkfields record = new Pkfields();
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setId1(1);
            record.setId2(2);
            mapper.insert(record);

            record = new Pkfields();
            record.setFirstname("Bob");
            record.setLastname("Jones");
            record.setId1(3);
            record.setId2(4);

            mapper.insert(record);

            record = new Pkfields();
            record.setFirstname("Fred");
            PkfieldsExample example = new PkfieldsExample();
            example.createCriteria().andLastnameLike("J%");
            int rows = mapper.updateByExampleSelective(record, example);
            assertEquals(1, rows);

            example.clear();
            example.createCriteria().andFirstnameEqualTo("Fred")
                    .andLastnameEqualTo("Jones").andId1EqualTo(3)
                    .andId2EqualTo(4);

            long returnedRows = mapper.countByExample(example);
            assertEquals(1, returnedRows);
        }
    }

    @Test
    public void testPKFieldsUpdateByExample() {

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            PkfieldsMapper mapper = sqlSession.getMapper(PkfieldsMapper.class);
            Pkfields record = new Pkfields();
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setId1(1);
            record.setId2(2);
            mapper.insert(record);

            record = new Pkfields();
            record.setFirstname("Bob");
            record.setLastname("Jones");
            record.setId1(3);
            record.setId2(4);

            mapper.insert(record);

            record = new Pkfields();
            record.setFirstname("Fred");
            record.setId1(3);
            record.setId2(4);
            PkfieldsExample example = new PkfieldsExample();
            example.createCriteria().andId1EqualTo(3).andId2EqualTo(4);

            int rows = mapper.updateByExample(record, example);
            assertEquals(1, rows);

            example.clear();
            example.createCriteria().andFirstnameEqualTo("Fred")
                    .andLastnameIsNull().andId1EqualTo(3).andId2EqualTo(4);

            long returnedRows = mapper.countByExample(example);
            assertEquals(1, returnedRows);
        }
    }

    @Test
    public void testPKBlobsUpdateByExampleSelective() {

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            PkblobsMapper mapper = sqlSession.getMapper(PkblobsMapper.class);
            PkblobsWithBLOBs record = new PkblobsWithBLOBs(3,
                    generateRandomBlob(), generateRandomBlob(), "Long String 1");
            mapper.insert(record);

            record = new PkblobsWithBLOBs(6, generateRandomBlob(),
                    generateRandomBlob(), "Long String 2");
            mapper.insert(record);

            PkblobsWithBLOBs newRecord = new PkblobsWithBLOBs(null,
                    generateRandomBlob(), null, null);

            PkblobsExample example = new PkblobsExample();
            example.createCriteria().andIdGreaterThan(4);
            int rows = mapper.updateByExampleSelective(newRecord, example);
            assertEquals(1, rows);

            List<PkblobsWithBLOBs> answer = mapper
                    .selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());

            PkblobsWithBLOBs returnedRecord = answer.get(0);

            assertEquals(6, returnedRecord.getId().intValue());
            assertTrue(blobsAreEqual(newRecord.getBlob1(),
                    returnedRecord.getBlob1()));
            assertTrue(blobsAreEqual(record.getBlob2(),
                    returnedRecord.getBlob2()));
            assertEquals(record.getCharacterlob(), returnedRecord.getCharacterlob());
        }
    }

    @Test
    public void testPKBlobsUpdateByExampleWithoutBLOBs() {

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            PkblobsMapper mapper = sqlSession.getMapper(PkblobsMapper.class);
            PkblobsWithBLOBs record = new PkblobsWithBLOBs(3,
                    generateRandomBlob(), generateRandomBlob(), "Long String 1");
            mapper.insert(record);

            record = new PkblobsWithBLOBs(6, generateRandomBlob(),
                    generateRandomBlob(), "Long String 2");
            mapper.insert(record);

            PkblobsKey newRecord = new PkblobsKey(8);

            PkblobsExample example = new PkblobsExample();
            example.createCriteria().andIdGreaterThan(4);
            int rows = mapper.updateByExample(newRecord, example);
            assertEquals(1, rows);

            List<PkblobsWithBLOBs> answer = mapper
                    .selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());

            PkblobsWithBLOBs returnedRecord = answer.get(0);

            assertEquals(8, returnedRecord.getId().intValue());
            assertTrue(blobsAreEqual(record.getBlob1(),
                    returnedRecord.getBlob1()));
            assertTrue(blobsAreEqual(record.getBlob2(),
                    returnedRecord.getBlob2()));
            assertEquals(record.getCharacterlob(), returnedRecord.getCharacterlob());
        }
    }

    @Test
    public void testPKBlobsUpdateByExampleWithBLOBs() {

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            PkblobsMapper mapper = sqlSession.getMapper(PkblobsMapper.class);
            PkblobsWithBLOBs record = new PkblobsWithBLOBs(3,
                    generateRandomBlob(), generateRandomBlob(), "Long String 1");
            mapper.insert(record);

            record = new PkblobsWithBLOBs(6, generateRandomBlob(),
                    generateRandomBlob(), "Long String 2");
            mapper.insert(record);

            PkblobsWithBLOBs newRecord = new PkblobsWithBLOBs(8, null, null, null);

            PkblobsExample example = new PkblobsExample();
            example.createCriteria().andIdGreaterThan(4);
            int rows = mapper.updateByExampleWithBLOBs(newRecord, example);
            assertEquals(1, rows);

            List<PkblobsWithBLOBs> answer = mapper
                    .selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());

            PkblobsWithBLOBs returnedRecord = answer.get(0);

            assertEquals(8, returnedRecord.getId().intValue());
            assertNull(returnedRecord.getBlob1());
            assertNull(returnedRecord.getBlob2());
            assertNull(returnedRecord.getCharacterlob());
        }
    }

    @Test
    public void testPKFieldsBlobsUpdateByExampleSelective() {

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            PkfieldsblobsMapper mapper = sqlSession
                    .getMapper(PkfieldsblobsMapper.class);
            PkfieldsblobsWithBLOBs record = new PkfieldsblobsWithBLOBs(3, 4,
                    "Jeff", "Smith", generateRandomBlob());
            mapper.insert(record);

            record = new PkfieldsblobsWithBLOBs(5, 6, "Scott", "Jones",
                    generateRandomBlob());
            mapper.insert(record);

            PkfieldsblobsWithBLOBs newRecord = new PkfieldsblobsWithBLOBs(null,
                    null, "Fred", null, null);
            PkfieldsblobsExample example = new PkfieldsblobsExample();
            example.createCriteria().andId1NotEqualTo(3);
            int rows = mapper.updateByExampleSelective(newRecord, example);
            assertEquals(1, rows);

            List<PkfieldsblobsWithBLOBs> answer = mapper
                    .selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());

            PkfieldsblobsWithBLOBs returnedRecord = answer.get(0);

            assertEquals(record.getId1(), returnedRecord.getId1());
            assertEquals(record.getId2(), returnedRecord.getId2());
            assertEquals(newRecord.getFirstname(),
                    returnedRecord.getFirstname());
            assertEquals(record.getLastname(), returnedRecord.getLastname());
            assertTrue(blobsAreEqual(record.getBlob1(),
                    returnedRecord.getBlob1()));

        }
    }

    @Test
    public void testPKFieldsBlobsUpdateByExampleWithoutBLOBs() {

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            PkfieldsblobsMapper mapper = sqlSession
                    .getMapper(PkfieldsblobsMapper.class);
            PkfieldsblobsWithBLOBs record = new PkfieldsblobsWithBLOBs(3, 4,
                    "Jeff", "Smith", generateRandomBlob());
            mapper.insert(record);

            record = new PkfieldsblobsWithBLOBs(5, 6, "Scott", "Jones",
                    generateRandomBlob());
            mapper.insert(record);

            Pkfieldsblobs newRecord = new Pkfieldsblobs(5, 8, "Fred", null);
            PkfieldsblobsExample example = new PkfieldsblobsExample();
            example.createCriteria().andId1EqualTo(5);
            int rows = mapper.updateByExample(newRecord, example);
            assertEquals(1, rows);

            List<PkfieldsblobsWithBLOBs> answer = mapper
                    .selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());

            PkfieldsblobsWithBLOBs returnedRecord = answer.get(0);

            assertEquals(newRecord.getId1(), returnedRecord.getId1());
            assertEquals(newRecord.getId2(), returnedRecord.getId2());
            assertEquals(newRecord.getFirstname(),
                    returnedRecord.getFirstname());
            assertNull(returnedRecord.getLastname());
            assertTrue(blobsAreEqual(record.getBlob1(),
                    returnedRecord.getBlob1()));

        }
    }

    @Test
    public void testPKFieldsBlobsUpdateByExampleWithBLOBs() {

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            PkfieldsblobsMapper mapper = sqlSession
                    .getMapper(PkfieldsblobsMapper.class);
            PkfieldsblobsWithBLOBs record = new PkfieldsblobsWithBLOBs(3, 4,
                    "Jeff", "Smith", generateRandomBlob());
            mapper.insert(record);

            record = new PkfieldsblobsWithBLOBs(5, 6, "Scott", "Jones",
                    generateRandomBlob());
            mapper.insert(record);

            PkfieldsblobsWithBLOBs newRecord = new PkfieldsblobsWithBLOBs(3, 8,
                    "Fred", null, null);
            PkfieldsblobsExample example = new PkfieldsblobsExample();
            example.createCriteria().andId1EqualTo(3);
            int rows = mapper.updateByExampleWithBLOBs(newRecord, example);
            assertEquals(1, rows);

            List<PkfieldsblobsWithBLOBs> answer = mapper
                    .selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());

            PkfieldsblobsWithBLOBs returnedRecord = answer.get(0);

            assertEquals(newRecord.getId1(), returnedRecord.getId1());
            assertEquals(newRecord.getId2(), returnedRecord.getId2());
            assertEquals(newRecord.getFirstname(),
                    returnedRecord.getFirstname());
            assertNull(returnedRecord.getLastname());
            assertNull(returnedRecord.getBlob1());

        }
    }

    @Test
    public void testFieldsBlobsUpdateByExampleSelective() {

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            FieldsblobsMapper mapper = sqlSession
                    .getMapper(FieldsblobsMapper.class);
            FieldsblobsWithBLOBs record = new FieldsblobsWithBLOBs("Jeff",
                    "Smith", generateRandomBlob(), generateRandomBlob(), null);
            mapper.insert(record);

            record = new FieldsblobsWithBLOBs("Scott", "Jones",
                    generateRandomBlob(), generateRandomBlob(), null);
            mapper.insert(record);

            FieldsblobsWithBLOBs newRecord = new FieldsblobsWithBLOBs(null,
                    "Doe", null, null, null);
            FieldsblobsExample example = new FieldsblobsExample();
            example.createCriteria().andFirstnameLike("S%");
            int rows = mapper.updateByExampleSelective(newRecord, example);
            assertEquals(1, rows);

            List<FieldsblobsWithBLOBs> answer = mapper
                    .selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());

            FieldsblobsWithBLOBs returnedRecord = answer.get(0);

            assertEquals(record.getFirstname(), returnedRecord.getFirstname());
            assertEquals(newRecord.getLastname(), returnedRecord.getLastname());
            assertTrue(blobsAreEqual(record.getBlob1(),
                    returnedRecord.getBlob1()));
            assertTrue(blobsAreEqual(record.getBlob2(),
                    returnedRecord.getBlob2()));
        }
    }

    @Test
    public void testFieldsBlobsUpdateByExampleWithoutBLOBs() {

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            FieldsblobsMapper mapper = sqlSession
                    .getMapper(FieldsblobsMapper.class);
            FieldsblobsWithBLOBs record = new FieldsblobsWithBLOBs("Jeff",
                    "Smith", generateRandomBlob(), generateRandomBlob(), null);
            mapper.insert(record);

            record = new FieldsblobsWithBLOBs("Scott", "Jones",
                    generateRandomBlob(), generateRandomBlob(), null);
            mapper.insert(record);

            Fieldsblobs newRecord = new Fieldsblobs("Scott", "Doe");
            FieldsblobsExample example = new FieldsblobsExample();
            example.createCriteria().andFirstnameLike("S%");
            int rows = mapper.updateByExample(newRecord, example);
            assertEquals(1, rows);

            List<FieldsblobsWithBLOBs> answer = mapper
                    .selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());

            FieldsblobsWithBLOBs returnedRecord = answer.get(0);

            assertEquals(newRecord.getFirstname(),
                    returnedRecord.getFirstname());
            assertEquals(newRecord.getLastname(), returnedRecord.getLastname());
            assertTrue(blobsAreEqual(record.getBlob1(),
                    returnedRecord.getBlob1()));
            assertTrue(blobsAreEqual(record.getBlob2(),
                    returnedRecord.getBlob2()));
        }
    }

    @Test
    public void testFieldsBlobsUpdateByExampleWithBLOBs() {

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            FieldsblobsMapper mapper = sqlSession
                    .getMapper(FieldsblobsMapper.class);
            FieldsblobsWithBLOBs record = new FieldsblobsWithBLOBs("Jeff",
                    "Smith", generateRandomBlob(), generateRandomBlob(), null);
            mapper.insert(record);

            record = new FieldsblobsWithBLOBs("Scott", "Jones",
                    generateRandomBlob(), generateRandomBlob(), null);
            mapper.insert(record);

            FieldsblobsWithBLOBs newRecord = new FieldsblobsWithBLOBs("Scott",
                    "Doe", null, null, null);
            FieldsblobsExample example = new FieldsblobsExample();
            example.createCriteria().andFirstnameLike("S%");
            int rows = mapper.updateByExampleWithBLOBs(newRecord, example);
            assertEquals(1, rows);

            List<FieldsblobsWithBLOBs> answer = mapper
                    .selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());

            FieldsblobsWithBLOBs returnedRecord = answer.get(0);

            assertEquals(newRecord.getFirstname(),
                    returnedRecord.getFirstname());
            assertEquals(newRecord.getLastname(), returnedRecord.getLastname());
            assertNull(returnedRecord.getBlob1());
            assertNull(returnedRecord.getBlob2());
        }
    }
}
