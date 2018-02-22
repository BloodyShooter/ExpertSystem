package org.gvozdetscky.demovaadin.lab1.client;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.gvozdetscky.demovaadin.lab1.server.Lab1;
import org.gvozdetscky.demovaadin.lab1.server.model.Record;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

@Theme("valo")
@Title("1 Лабороторная")
@SpringUI(path = "/lab1")
public class Lab1Client extends UI {

	@Autowired
	private Lab1 lab1;

	private VerticalLayout layout = new VerticalLayout();
	private Label resultLabel = new Label();
	private Grid<Record> recordGrid = new Grid<>("Сгенерируемые значения");

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		recordGrid.addColumn((ValueProvider<Record, Double>) Record::getParam1).setCaption("1 параметр");
		recordGrid.addColumn((ValueProvider<Record, Double>) Record::getParam2).setCaption("2 параметр");
		recordGrid.addColumn((ValueProvider<Record, Double>) Record::getParam3).setCaption("3 параметр");
		recordGrid.setSizeFull();

		generate();

		Button euclideanDistanceBtn = new Button("По эвклиду");
		euclideanDistanceBtn.addClickListener(click -> {
			resultLabel.setValue(
					"Алгоритм работал " + lab1.run(Lab1.EVCLID_MOD) + " раз по эвклиду");
		});

		Button manhatanDistanceBtn = new Button("По манхэтану");
		manhatanDistanceBtn.addClickListener(click -> {
			resultLabel.setValue(
					"Алгоритм работал " + lab1.run(Lab1.MANCHTAN_MOD)  + " раз по манхэтану");
		});

		Button generateBtn = new Button("генерировать");
		generateBtn.setStyleName(ValoTheme.BUTTON_PRIMARY);
		generateBtn.addClickListener(click -> {
			generate();
			resultLabel.setValue("");
		});

		Label label = new Label("Лабораторная 1");
		label.setStyleName(ValoTheme.LABEL_H1);

		HorizontalLayout layoutBtn = new HorizontalLayout();
		layoutBtn.addComponents(euclideanDistanceBtn, manhatanDistanceBtn, generateBtn);

		layout.addComponents(label, layoutBtn, recordGrid, resultLabel);

		setContent(layout);
	}

	private void generate() {
		final List<Record> records = lab1.generateRecord();
		recordGrid.setItems(records);
	}
}
