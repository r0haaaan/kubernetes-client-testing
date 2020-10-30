/**
 * Copyright (C) 2015 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.fabric8;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.fabric8.kubernetes.api.model.Quantity;
import io.fabric8.kubernetes.api.model.apiextensions.v1beta1.CustomResourceDefinition;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuantityTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testAmountUnitSeparately() throws JsonProcessingException {
        Quantity quantity = new Quantity("256", "Mi");
        String serializedObj = mapper.writeValueAsString(quantity);
        assertEquals("\"256Mi\"", serializedObj);
    }

    @Test
    public void testAmount() throws JsonProcessingException {
        Quantity quantity = new Quantity("256Mi");
        String serializedObj = mapper.writeValueAsString(quantity);
        assertEquals("\"256Mi\"", serializedObj);
    }

    @Test
    public void testQuantityObj() {
        Quantity quantity = new Quantity("32Mi");
        assertEquals("32", quantity.getAmount());
        assertEquals("Mi", quantity.getFormat());
    }

    @Test
    public void testNormalization() {
        Quantity quantity = new Quantity(".5Mi");
        assertEquals(new BigDecimal("524288.0"), Quantity.getAmountInBytes(quantity));

        Quantity quantity1 =  new Quantity("512Ki");
        assertEquals(Quantity.getAmountInBytes(quantity1).toBigInteger(), Quantity.getAmountInBytes(quantity).toBigInteger());
    }

    @Test
    public void testNegativeExponents() {
        Quantity quantity1 = new Quantity("100001m");
        Quantity quantity2 = new Quantity("1n");

        assertEquals("100.001", Quantity.getAmountInBytes(quantity1).toString());
        assertEquals("1E-9", Quantity.getAmountInBytes(quantity2).toString());
    }

    @Test
    public void testExponents() {
        assertEquals("129000000", Quantity.getAmountInBytes(new Quantity("129e6")).toString());
        assertEquals("129000000", Quantity.getAmountInBytes(new Quantity("129e+6")).toString());
        assertEquals("8192", Quantity.getAmountInBytes(new Quantity("8Ki")).toString());
        assertEquals("7340032", Quantity.getAmountInBytes(new Quantity("7Mi")).toString());
        assertEquals("6442450944", Quantity.getAmountInBytes(new Quantity("6Gi")).toString());
        assertEquals("5497558138880", Quantity.getAmountInBytes(new Quantity("5Ti")).toString());
        assertEquals("4503599627370496", Quantity.getAmountInBytes(new Quantity("4Pi")).toString());
        assertEquals("3000000000000000000", Quantity.getAmountInBytes(new Quantity("3Ei")).toString());
        assertEquals("5E-9", Quantity.getAmountInBytes(new Quantity("5n")).toString());
        assertEquals("0.000004", Quantity.getAmountInBytes(new Quantity("4u")).toString());
        assertEquals("0.003", Quantity.getAmountInBytes(new Quantity("3m")).toString());
        assertEquals("9", Quantity.getAmountInBytes(new Quantity("9")).toString());
        assertEquals("8000", Quantity.getAmountInBytes(new Quantity("8k")).toString());
        assertEquals("50000", Quantity.getAmountInBytes(new Quantity("50k")).toString());
        assertEquals("7000000", Quantity.getAmountInBytes(new Quantity("7M")).toString());
        assertEquals("6000000000", Quantity.getAmountInBytes(new Quantity("6G")).toString());
        assertEquals("5000000000000", Quantity.getAmountInBytes(new Quantity("5T")).toString());
        assertEquals("40000000000000", Quantity.getAmountInBytes(new Quantity("40T")).toString());
        assertEquals("300000000000000", Quantity.getAmountInBytes(new Quantity("300T")).toString());
        assertEquals("2000000000000000", Quantity.getAmountInBytes(new Quantity("2P")).toString());
        assertEquals("1.000000000000000E+18", Quantity.getAmountInBytes(new Quantity("1E")).toString());

    }

    @Test
    public void testEquality() {
        assertTrue(new Quantity(".5Mi").equals( new Quantity("512Ki")));
    }

    @Test
    public void testExponent() {
        assertEquals("10000", Quantity.getAmountInBytes(new Quantity("1e4")).toString());
    }

    @Test
    public void testFractions() {
        assertNotEquals(new Quantity("100m"), new Quantity("200m"));
        assertEquals(new Quantity("100m"), new Quantity("100m"));
    }

    @Test
    public void testStringConversion() {
        assertEquals(".5Mi", new Quantity(".5Mi").toString());
        assertEquals("1", new Quantity("1").toString());
        assertEquals("129e6", new Quantity("129e6").toString());
        assertEquals("0.001m", new Quantity("0.001m").toString());
        assertEquals("1Ki", new Quantity("1Ki").toString());
        assertEquals("32Mi", new Quantity("32Mi").toString());
        assertEquals("1e3", new Quantity("1e3").toString());
        assertEquals("1e-3", new Quantity("1e-3").toString());
        assertEquals("100k", new Quantity("100k").toString());
        assertEquals("100001m", new Quantity("100001m").toString());
        assertEquals("1Mi", new Quantity("1Mi"));
    }
    @Test
    public void testParseFailure() {
        assertThrows(IllegalArgumentException.class, () -> new Quantity("4e9e"));
        assertThrows(IllegalArgumentException.class, () -> new Quantity(""));
        assertThrows(IllegalArgumentException.class, () -> new Quantity(null));
    }

@Test
public void testOnAdd() {
    try (final KubernetesClient client = new DefaultKubernetesClient()) {
        // Given
        CustomResourceDefinition animalCrd = client.customResourceDefinitions()
                .load(getClass().getResourceAsStream("/animal.yml")).get();

        // When
        animalCrd = client.customResourceDefinitions().create(animalCrd);

        // Then
        assertNotNull(animalCrd);
    }
}
}
