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
import jsass.internal.parser.ParseException;
import jsass.internal.parser.SassList;
import jsass.internal.parser.SassListItem;

public class ListNthFunctionGenerator extends AbstractFunctionGenerator {

    private static String[] argumentNames = { "list", "n" };

    public ListNthFunctionGenerator() {
        super(createArgumentList(argumentNames, false), "nth");
    }

    @Override
    protected SassListItem computeForArgumentList(LexicalUnitImpl function,
            FormalArgumentList actualArguments) {
        SassListItem listAsItem = getParam(actualArguments, "list");
        if (!(listAsItem instanceof SassList)) {
            listAsItem = new SassList(listAsItem);
        }
        SassListItem nAsItem = getParam(actualArguments, "n");
        if (!(nAsItem instanceof LexicalUnitImpl)
                || ((LexicalUnitImpl) nAsItem).getLexicalUnitType() != LexicalUnitImpl.SAC_INTEGER) {
            throw new ParseException(
                    "The second parameter of nth() must be an integer. Actual value: "
                            + nAsItem);
        }
        SassList list = (SassList) listAsItem;
        int n = ((LexicalUnitImpl) nAsItem).getIntegerValue();
        if (n < 1 || n > list.size()) {
            throw new ParseException(
                    "Index out of range for the function nth(). List: " + list
                            + ", index: " + n);
        }
        return list.get(n - 1);
    }
}
