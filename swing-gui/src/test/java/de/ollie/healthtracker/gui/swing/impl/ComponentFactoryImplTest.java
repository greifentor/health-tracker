package de.ollie.healthtracker.gui.swing.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import javax.swing.JButton;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ComponentFactoryImplTest {

	private static final String LABEL = "label";

	@InjectMocks
	private ComponentFactoryImpl unitUnderTest;

	@Nested
	class createButton_String {

		@Test
		void returnsANewButton() {
			assertNotNull(unitUnderTest.createButton(LABEL));
		}

		@Test
		void returnsANewButton_withCorrectLabel() {
			// Run
			JButton button = unitUnderTest.createButton(LABEL);
			// Check
			assertEquals(LABEL, button.getText());
		}

		@Test
		void returnsANewButton_withAnEmptyLabel_passingANullValue() {
			// Run
			JButton button = unitUnderTest.createButton(null);
			// Check
			assertEquals("", button.getText());
		}

		@Test
		void returnsANewButton_onEachCall() {
			assertNotSame(unitUnderTest.createButton(LABEL), unitUnderTest.createButton(LABEL));
		}
	}
}
