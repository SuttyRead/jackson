

                      package com.ua.sutty.jackson.domain;

                      import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
                      import lombok.AllArgsConstructor;
                      import lombok.Data;
                      import lombok.NoArgsConstructor;

                      @Data
                      @AllArgsConstructor
                      @NoArgsConstructor
                      @JsonIgnoreProperties("firstName")
                      public class Person
                      {
                        private String firstName;
                        private int age;
                      }


