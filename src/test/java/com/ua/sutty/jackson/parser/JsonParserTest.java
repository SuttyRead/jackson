package com.ua.sutty.jackson.parser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ua.sutty.jackson.domain.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class JsonParserTest
{
  private Person[] expectedPersons;
  private static final String file = "persons.json";

  @BeforeEach
  public void setUp()
  {
    Person firstPerson = new Person("Andrew", 18);
    Person secondPerson = new Person("Kate", 25);
    Person thirdPerson = new Person("John", 35);
    Person fourthPerson = new Person("Ann", 40);
    expectedPersons = new Person[]{firstPerson, secondPerson, thirdPerson, fourthPerson};
  }

  @Test
  public void dataBindingParserTest() throws IOException
  {
    ObjectMapper objectMapper = new ObjectMapper();
    Person[] actualPersons = objectMapper.readValue(new File(file), Person[].class);
    Assertions.assertArrayEquals(expectedPersons, actualPersons);
  }

  @Test
  public void treeModelParserTest() throws IOException
  {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.readValue(new File(file), JsonNode.class);
    Assertions.assertEquals(expectedPersons[0].getFirstName(), jsonNode.get(0).get("firstName").asText());
  }

  @Test
  public void StreamingParserTest() throws IOException
  {
    JsonFactory factory = new JsonFactory();
    JsonParser jsonParser = factory.createParser(new File(file));

    JsonToken token = jsonParser.nextToken(); // [
    token = jsonParser.nextToken(); // {
    while (jsonParser.nextToken() != JsonToken.END_OBJECT)
    {
      String fieldName = jsonParser.getCurrentName();
      if (fieldName.equals("firstName"))
      {
        jsonParser.nextToken();
        Assertions.assertEquals(expectedPersons[0].getFirstName(), jsonParser.getText());
      }
    }
    jsonParser.close();
  }
}
