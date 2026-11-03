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

import java.util.ArrayList;
import java.util.List;

import jsass.internal.parser.ActualArgumentList;
import jsass.internal.parser.FormalArgumentList;
import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.ParseException;
import jsass.internal.parser.SassList;
import jsass.internal.parser.SassListItem;

public class RGBFunctionGenerator extends AbstractFunctionGenerator {

    private static String[] argumentNames = { "red", "green", "blue" };

    public RGBFunctionGenerator() {
        super(createArgumentList(argumentNames, false), "rgb");
    }

    @Override
    protected SassListItem computeForArgumentList(LexicalUnitImpl function,
            FormalArgumentList actualArguments) {
        List<SassListItem> components = new ArrayList<SassListItem>();
        int line = function.getLineNumber();
        int column = function.getColumnNumber();
        for (int i = 0; i < 3; ++i) {
            SassListItem item = getParam(actualArguments, i);
            if (!LexicalUnitImpl
                    .checkLexicalUnitType(item, LexicalUnitImpl.SAC_INTEGER,
                            LexicalUnitImpl.SAC_PERCENTAGE)) {
                throw new ParseException(
                        "Invalid parameter to the function rgb(): "
                                + item.toString(), line, column);
            }
            components.add(item);
        }
        ActualArgumentList params = new ActualArgumentList(
                SassList.Separator.COMMA, components);
        return LexicalUnitImpl.createRGBColor(line, column, params);
    }
}
