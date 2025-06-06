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
package org.mybatis.generator.api.dom.xml;

import java.util.ArrayList;
import java.util.List;

public class XmlElement implements VisitableElement {

    private final List<Attribute> attributes = new ArrayList<>();

    private final List<VisitableElement> elements = new ArrayList<>();

    private String name;

    public XmlElement(String name) {
        this.name = name;
    }

    /**
     * Copy constructor. Not a truly deep copy, but close enough for most purposes.
     *
     * @param original
     *            the original
     */
    public XmlElement(XmlElement original) {
        super();
        attributes.addAll(original.attributes);
        elements.addAll(original.elements);
        this.name = original.name;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void addAttribute(Attribute attribute) {
        attributes.add(attribute);
    }

    public List<VisitableElement> getElements() {
        return elements;
    }

    public void addElement(VisitableElement element) {
        elements.add(element);
    }

    public void addElement(int index, VisitableElement element) {
        elements.add(index, element);
    }

    public String getName() {
        return name;
    }

    public boolean hasChildren() {
        return !elements.isEmpty();
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public <R> R accept(ElementVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
