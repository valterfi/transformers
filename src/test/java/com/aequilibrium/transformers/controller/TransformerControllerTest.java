package com.aequilibrium.transformers.controller;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.aequilibrium.transformers.enums.TransformerType;
import com.aequilibrium.transformers.exception.TransformerException;
import com.aequilibrium.transformers.exception.TransformerExceptionResponse;
import com.aequilibrium.transformers.model.Transformer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author valterfi
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class TransformerControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void shouldFindAllTest() throws Exception {
		mockMvc.perform( MockMvcRequestBuilders
			      .get("/api/transformers")
			      .accept(MediaType.APPLICATION_JSON))
			      .andDo(print())
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$", hasSize(15)))
			      .andExpect(jsonPath("$[0].id").value(15))
			      .andExpect(jsonPath("$[1].id").value(14))
			      .andExpect(jsonPath("$[2].id").value(13))
			      .andExpect(jsonPath("$[3].id").value(12))
			      .andExpect(jsonPath("$[4].id").value(11))
			      .andExpect(jsonPath("$[5].id").value(10))
			      .andExpect(jsonPath("$[6].id").value(9))
			      .andExpect(jsonPath("$[7].id").value(8))
			      .andExpect(jsonPath("$[8].id").value(7))
			      .andExpect(jsonPath("$[9].id").value(6))
			      .andExpect(jsonPath("$[10].id").value(5))
			      .andExpect(jsonPath("$[11].id").value(4))
			      .andExpect(jsonPath("$[12].id").value(3))
			      .andExpect(jsonPath("$[13].id").value(2))
			      .andExpect(jsonPath("$[14].id").value(1));
	}
	
	@Test
	public void shouldFindAllWithPaginationFirstPageTest() throws Exception {
		mockMvc.perform( MockMvcRequestBuilders
			      .get("/api/transformers?pageNo=0&pageSize=10")
			      .accept(MediaType.APPLICATION_JSON))
			      .andDo(print())
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$", hasSize(10)))
			      .andExpect(jsonPath("$[0].id").value(15))
			      .andExpect(jsonPath("$[1].id").value(14))
			      .andExpect(jsonPath("$[2].id").value(13))
			      .andExpect(jsonPath("$[3].id").value(12))
			      .andExpect(jsonPath("$[4].id").value(11))
			      .andExpect(jsonPath("$[5].id").value(10))
			      .andExpect(jsonPath("$[6].id").value(9))
			      .andExpect(jsonPath("$[7].id").value(8))
			      .andExpect(jsonPath("$[8].id").value(7))
			      .andExpect(jsonPath("$[9].id").value(6));
	}
	
	@Test
	public void shouldFindAllWithPaginationSecondPageTest() throws Exception {
		mockMvc.perform( MockMvcRequestBuilders
			      .get("/api/transformers?pageNo=1&pageSize=10")
			      .accept(MediaType.APPLICATION_JSON))
			      .andDo(print())
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$").isNotEmpty())
			      .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(5))));
	}
	
	@Test
	public void shouldReturnSingleTransformerTest() throws Exception {
		mockMvc.perform( MockMvcRequestBuilders
			      .get("/api/transformers/5")
	    		  .contentType(MediaType.APPLICATION_JSON)
	    	      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$").isNotEmpty())
			      .andExpect(jsonPath("$.id").value(5));
	}
	
	@Test
	public void shouldNotFindTransformerTest() throws Exception {
		MvcResult mvcResult = mockMvc.perform( MockMvcRequestBuilders
			      .get("/api/transformers/123")
	    		  .contentType(MediaType.APPLICATION_JSON)
	    	      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isNotFound())
			      .andExpect(result -> assertTrue(result.getResolvedException() instanceof TransformerException))
			      .andReturn();
		
		String content = mvcResult.getResponse().getContentAsString();
		TransformerExceptionResponse transformerExceptionResponse = convertToResponse(content);
		
		assertEquals("NOT_FOUND", transformerExceptionResponse.getError());
		assertEquals(404, transformerExceptionResponse.getStatus());
		assertEquals("Transformer not found", transformerExceptionResponse.getMessage());
		assertNotNull(transformerExceptionResponse.getTimestamp());
	}
	
	@Test
	public void shouldAddNewAutobotTransformerTest() throws Exception {
		Transformer newAutobot = Transformer.builder().withName("newAutobot")
									.withTransformerType(TransformerType.AUTOBOT)
									.withStrength(4)
									.withIntelligence(4)
									.withSpeed(4)
									.withEndurance(4)
									.withRank(4)
									.withCourage(4)
									.withFirepower(4)
									.withSkill(4);
		
		mockMvc.perform( MockMvcRequestBuilders
			      .post("/api/transformers")
			      .content(asJsonString(newAutobot))
	    		  .contentType(MediaType.APPLICATION_JSON)
	    	      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isCreated())
			      .andExpect(jsonPath("$.id").isNotEmpty())
			      .andExpect(jsonPath("$.name").value("newAutobot"))
			      .andExpect(jsonPath("$.transformerType").value("AUTOBOT"))
			      .andExpect(jsonPath("$.strength").value(4))
			      .andExpect(jsonPath("$.intelligence").value(4))
			      .andExpect(jsonPath("$.speed").value(4))
			      .andExpect(jsonPath("$.endurance").value(4))
			      .andExpect(jsonPath("$.rank").value(4))
			      .andExpect(jsonPath("$.courage").value(4))
			      .andExpect(jsonPath("$.firepower").value(4))
			      .andExpect(jsonPath("$.skill").value(4));
	}
	
	@Test
	public void shouldAddNewDecepticonTransformerTest() throws Exception {
		Transformer newDecepticon = Transformer.builder().withName("newDecepticon")
									.withTransformerType(TransformerType.DECEPTICON)
									.withStrength(2)
									.withIntelligence(2)
									.withSpeed(2)
									.withEndurance(2)
									.withRank(2)
									.withCourage(2)
									.withFirepower(2)
									.withSkill(2);
		
		mockMvc.perform( MockMvcRequestBuilders
			      .post("/api/transformers")
			      .content(asJsonString(newDecepticon))
	    		  .contentType(MediaType.APPLICATION_JSON)
	    	      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isCreated())
			      .andExpect(jsonPath("$.id").isNotEmpty())
			      .andExpect(jsonPath("$.name").value("newDecepticon"))
			      .andExpect(jsonPath("$.transformerType").value("DECEPTICON"))
			      .andExpect(jsonPath("$.strength").value(2))
			      .andExpect(jsonPath("$.intelligence").value(2))
			      .andExpect(jsonPath("$.speed").value(2))
			      .andExpect(jsonPath("$.endurance").value(2))
			      .andExpect(jsonPath("$.rank").value(2))
			      .andExpect(jsonPath("$.courage").value(2))
			      .andExpect(jsonPath("$.firepower").value(2))
			      .andExpect(jsonPath("$.skill").value(2));
	}
	
	@Test
	public void shouldThrowExpectionAddWithIdTest() throws Exception {
		Transformer tranformerWithId = Transformer.builder().withName("tranformerWithId")
									.withTransformerType(TransformerType.DECEPTICON)
									.withStrength(2)
									.withIntelligence(2)
									.withSpeed(2)
									.withEndurance(2)
									.withRank(2)
									.withCourage(2)
									.withFirepower(2)
									.withSkill(2);
		tranformerWithId.setId(123L);
		
		MvcResult mvcResult = mockMvc.perform( MockMvcRequestBuilders
			      .post("/api/transformers")
			      .content(asJsonString(tranformerWithId))
	    		  .contentType(MediaType.APPLICATION_JSON)
	    	      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isBadRequest())
			      .andExpect(result -> assertTrue(result.getResolvedException() instanceof TransformerException))
			      .andReturn();
		
		String content = mvcResult.getResponse().getContentAsString();
		TransformerExceptionResponse transformerExceptionResponse = convertToResponse(content);
		
		assertEquals("BAD_REQUEST", transformerExceptionResponse.getError());
		assertEquals(400, transformerExceptionResponse.getStatus());
		assertEquals("It is not possible to add a transform with Id", transformerExceptionResponse.getMessage());
		assertNotNull(transformerExceptionResponse.getTimestamp());
	}

	@Test
	public void shouldThrowExpectionAddSpecLessThanOneTest() throws Exception {
		Transformer tranformerWithId = Transformer.builder().withName("tranformerWithId")
									.withTransformerType(TransformerType.DECEPTICON)
									.withStrength(0)
									.withIntelligence(2)
									.withSpeed(2)
									.withEndurance(2)
									.withRank(2)
									.withCourage(2)
									.withFirepower(2)
									.withSkill(2);
		
		MvcResult mvcResult = mockMvc.perform( MockMvcRequestBuilders
			      .post("/api/transformers")
			      .content(asJsonString(tranformerWithId))
	    		  .contentType(MediaType.APPLICATION_JSON)
	    	      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isInternalServerError())
			      .andExpect(result -> assertTrue(result.getResolvedException() instanceof TransformerException))
			      .andReturn();
		
		String content = mvcResult.getResponse().getContentAsString();
		TransformerExceptionResponse transformerExceptionResponse = convertToResponse(content);
		
		assertEquals("INTERNAL_SERVER_ERROR", transformerExceptionResponse.getError());
		assertEquals(500, transformerExceptionResponse.getStatus());
		assertEquals("strength must be between 1 and 10", transformerExceptionResponse.getMessage());
		assertNotNull(transformerExceptionResponse.getTimestamp());
	}
	
	@Test
	public void shouldUpdateTransformerTest() throws Exception {
		Transformer updatedTransformer = Transformer.builder().withName("updatedTransformer")
									.withTransformerType(TransformerType.DECEPTICON)
									.withStrength(10)
									.withIntelligence(10)
									.withSpeed(10)
									.withEndurance(10)
									.withRank(10)
									.withCourage(10)
									.withFirepower(10)
									.withSkill(10);
		
		mockMvc.perform( MockMvcRequestBuilders
			      .put("/api/transformers/1")
			      .content(asJsonString(updatedTransformer))
	    		  .contentType(MediaType.APPLICATION_JSON)
	    	      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.id").value(1))
			      .andExpect(jsonPath("$.name").value("updatedTransformer"))
			      .andExpect(jsonPath("$.transformerType").value("DECEPTICON"))
			      .andExpect(jsonPath("$.strength").value(10))
			      .andExpect(jsonPath("$.intelligence").value(10))
			      .andExpect(jsonPath("$.speed").value(10))
			      .andExpect(jsonPath("$.endurance").value(10))
			      .andExpect(jsonPath("$.rank").value(10))
			      .andExpect(jsonPath("$.courage").value(10))
			      .andExpect(jsonPath("$.firepower").value(10))
			      .andExpect(jsonPath("$.skill").value(10));
	}
	
	@Test
	public void shouldUpdateTransformerWithSpecGreaterThanTenTest() throws Exception {
		Transformer updatedTransformer = Transformer.builder().withName("updatedTransformer")
									.withTransformerType(TransformerType.DECEPTICON)
									.withStrength(10)
									.withIntelligence(10)
									.withSpeed(10)
									.withEndurance(11)
									.withRank(10)
									.withCourage(10)
									.withFirepower(10)
									.withSkill(10);
		
		MvcResult mvcResult = mockMvc.perform( MockMvcRequestBuilders
					.put("/api/transformers/1")
					.content(asJsonString(updatedTransformer))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
				   	.andExpect(status().isInternalServerError())
				   	.andExpect(result -> assertTrue(result.getResolvedException() instanceof TransformerException))
				   	.andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		TransformerExceptionResponse transformerExceptionResponse = convertToResponse(content);
		
		assertEquals("INTERNAL_SERVER_ERROR", transformerExceptionResponse.getError());
		assertEquals(500, transformerExceptionResponse.getStatus());
		assertEquals("endurance must be between 1 and 10", transformerExceptionResponse.getMessage());
		assertNotNull(transformerExceptionResponse.getTimestamp());
	}
	
	@Test
	public void shouldUpdateTransformerWithIdNotFoundTest() throws Exception {
		Transformer updatedTransformer = Transformer.builder().withName("updatedTransformer")
									.withTransformerType(TransformerType.DECEPTICON)
									.withStrength(10)
									.withIntelligence(10)
									.withSpeed(10)
									.withEndurance(11)
									.withRank(10)
									.withCourage(10)
									.withFirepower(10)
									.withSkill(10);
		
		MvcResult mvcResult = mockMvc.perform( MockMvcRequestBuilders
					.put("/api/transformers/123")
					.content(asJsonString(updatedTransformer))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
				   	.andExpect(status().isNotFound())
				   	.andExpect(result -> assertTrue(result.getResolvedException() instanceof TransformerException))
				   	.andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		TransformerExceptionResponse transformerExceptionResponse = convertToResponse(content);
		
		assertEquals("NOT_FOUND", transformerExceptionResponse.getError());
		assertEquals(404, transformerExceptionResponse.getStatus());
		assertEquals("Transformer not found", transformerExceptionResponse.getMessage());
		assertNotNull(transformerExceptionResponse.getTimestamp());
	}
	
	@Test
	public void shouldDeleteSingleTransformerTest() throws Exception {
		mockMvc.perform( MockMvcRequestBuilders
			      .delete("/api/transformers/3")
	    		  .contentType(MediaType.APPLICATION_JSON)
	    	      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk());
	}
	
	@Test
	public void shouldNotDeleteSingleTransformerTest() throws Exception {
		MvcResult mvcResult = mockMvc.perform( MockMvcRequestBuilders
			      .delete("/api/transformers/123")
	    		  .contentType(MediaType.APPLICATION_JSON)
	    	      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isNotFound())
			      .andExpect(result -> assertTrue(result.getResolvedException() instanceof TransformerException))
			      .andReturn();
		
		String content = mvcResult.getResponse().getContentAsString();
		TransformerExceptionResponse transformerExceptionResponse = convertToResponse(content);
		
		assertEquals("NOT_FOUND", transformerExceptionResponse.getError());
		assertEquals(404, transformerExceptionResponse.getStatus());
		assertEquals("Transformer not found", transformerExceptionResponse.getMessage());
		assertNotNull(transformerExceptionResponse.getTimestamp());
	}
	
	@Test
	public void shouldDeleteAllTransformerTest() throws Exception {
		mockMvc.perform( MockMvcRequestBuilders
			      .delete("/api/transformers/deleteAll")
	    		  .contentType(MediaType.APPLICATION_JSON)
	    	      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk());
	}
	
	@Test
	public void shouldGenerateRandomTransformers() throws Exception {
		mockMvc.perform( MockMvcRequestBuilders
			      .post("/api/transformers/random/20")
	    		  .contentType(MediaType.APPLICATION_JSON)
	    	      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk());
	}
	
	@Test
	public void shouldNotGenerateRandomTransformers() throws Exception {
		MvcResult mvcResult = mockMvc.perform( MockMvcRequestBuilders
			      .post("/api/transformers/random/50000")
	    		  .contentType(MediaType.APPLICATION_JSON)
	    	      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isInternalServerError())
			      .andExpect(result -> assertTrue(result.getResolvedException() instanceof TransformerException))
			      .andReturn();
		
		String content = mvcResult.getResponse().getContentAsString();
		TransformerExceptionResponse transformerExceptionResponse = convertToResponse(content);
		
		assertEquals("INTERNAL_SERVER_ERROR", transformerExceptionResponse.getError());
		assertEquals(500, transformerExceptionResponse.getStatus());
		assertTrue(transformerExceptionResponse.getMessage().contains("Fighters number must be less than 50000")); 
		assertNotNull(transformerExceptionResponse.getTimestamp());
	}
	
	private TransformerExceptionResponse convertToResponse(String content) throws JsonProcessingException, JsonMappingException {
		ObjectMapper mapper = new ObjectMapper();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		mapper.setDateFormat(dateFormat);
		TransformerExceptionResponse transformerExceptionResponse = mapper.readValue(content, TransformerExceptionResponse.class);
		return transformerExceptionResponse;
	}
	
	public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
