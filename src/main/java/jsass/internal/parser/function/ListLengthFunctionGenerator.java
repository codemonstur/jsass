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
package jsass.internal.parser.function;

import jsass.internal.parser.FormalArgumentList;
import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.SassList;
import jsass.internal.parser.SassListItem;

public class ListLengthFunctionGenerator extends AbstractFunctionGenerator {

    private static String[] argumentNames = { "list" };

    public ListLengthFunctionGenerator() {
        super(createArgumentList(argumentNames, false), "length");
    }

    @Override
    protected SassListItem computeForArgumentList(LexicalUnitImpl function,
            FormalArgumentList actualArguments) {
        int length;
        SassListItem list = getParam(actualArguments, "list");
        if (list instanceof SassList) {
            length = ((SassList) list).size();
        } else {
            length = 1;
        }
        return LexicalUnitImpl.createInteger(function.getLineNumber(),
                function.getColumnNumber(), length);
    }

}
