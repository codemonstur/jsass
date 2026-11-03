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

import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import jsass.internal.ScssContext;
import jsass.internal.parser.Variable;
import jsass.internal.util.StringUtil;

public class ConsoleMessageNode extends Node implements IVariableNode {

    private String message;
    private boolean warning;

    public ConsoleMessageNode(String message, boolean warning) {
        this.message = message;
        this.warning = warning;
    }

    @Override
    public void replaceVariables(ScssContext context) {
        // interpolation of variable names in the string
        for (final Variable var : context.getVariables()) {
            if (StringUtil.containsVariable(message, var.getName())) {
                message = StringUtil.replaceVariable(message, var.getName(),
                        var.getExpr().printState());
            }
        }
    }

    @Override
    public String printState() {
        return "";
    }

    @Override
    public String toString() {
        return (warning ? "@warn " : "@debug ") + message;
    }

    @Override
    public Collection<Node> traverse(ScssContext context) {
        Level level = warning ? Level.SEVERE : Level.INFO;
        Logger.getLogger(ConsoleMessageNode.class.getName())
                .log(level, message);
        return Collections.emptyList();
    }

    @Override
    public ConsoleMessageNode copy() {
        return new ConsoleMessageNode(message, warning);
    }
}
