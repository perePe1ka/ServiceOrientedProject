package ru.vladuss.serviceorientedproject.model;

import org.springframework.hateoas.RepresentationModel;

public class ActionModel extends RepresentationModel<ActionModel> {
    private String actionName;
    private String description;

    public ActionModel(String actionName, String description) {
        this.actionName = actionName;
        this.description = description;
    }

    // Геттеры и сеттеры

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
