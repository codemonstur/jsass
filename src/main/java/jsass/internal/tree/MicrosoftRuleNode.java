/*
 * Copyright 2000-2014 Vaadin Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package jsass.internal.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import jsass.internal.ScssContext;
import jsass.internal.parser.SassListItem;
import jsass.internal.parser.StringInterpolationSequence;
import jsass.internal.parser.StringItem;
import jsass.internal.parser.Variable;
import jsass.internal.util.StringUtil;

public class MicrosoftRuleNode extends Node implements IVariableNode {

    private final String name;
    private StringInterpolationSequence value;

    public MicrosoftRuleNode(String name, StringInterpolationSequence value) {
        this.name = name;
        this.value = value;
    }

    private MicrosoftRuleNode(MicrosoftRuleNode nodeToCopy) {
        super(nodeToCopy);
        name = nodeToCopy.name;
        value = nodeToCopy.value;
    }

    @Override
    public void replaceVariables(ScssContext context) {
        boolean variableReplaced = false;
        value = value.replaceVariables(context);
        // Replace variables occurring in quoted strings
        ArrayList<SassListItem> items = new ArrayList<SassListItem>();
        for (SassListItem item : value.getItems()) {
            if (!(item instanceof StringItem)) {
                items.add(item);
                continue;
            }
            String stringValue = item.printState();
            for (final Variable var : context.getVariables()) {
                if (StringUtil.containsVariable(stringValue, var.getName())) {
                    variableReplaced = true;
                    stringValue = StringUtil.replaceVariable(stringValue,
                            var.getName(), var.getExpr().printState());
                }
            }
            items.add(new StringItem(stringValue));
        }
        if (variableReplaced) {
            value = new StringInterpolationSequence(items);
        }
    }

    @Override
    public String printState() {
        return name + ": " + value + ";";
    }

    @Override
    public String toString() {
        return "MicrosoftRule node [" + printState() + "]";
    }

    @Override
    public Collection<Node> traverse(ScssContext context) {
        replaceVariables(context);
        traverseChildren(context);
        return Collections.singleton((Node) this);
    }

    @Override
    public MicrosoftRuleNode copy() {
        return new MicrosoftRuleNode(this);
    }
}
