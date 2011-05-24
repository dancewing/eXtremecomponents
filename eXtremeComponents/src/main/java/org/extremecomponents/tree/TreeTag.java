/*
 * Copyright 2004 original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.extremecomponents.tree;

import org.extremecomponents.table.bean.Table;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.tag.TableTag;
import org.extremecomponents.table.tag.TagUtils;

/**
 * @jsp.tag name="tree" display-name="TreeTag" body-content="JSP"
 *          description="Defines everything related to tree."
 * 
 * @author Paul Horn
 */
public class TreeTag extends TableTag {
    private String parentAttribute;
    private String identifier;

    /**
     * @jsp.attribute description="The field of the bean holding the
     *                relationship to the parent." required="true"
     *                rtexprvalue="true"
     * 
     * @return Returns the parentAttribute.
     */
    public void setParentAttribute(String parentAttribute) {
        this.parentAttribute = parentAttribute;
    }

    /**
     * @jsp.attribute description="The attribute of the bean used to identify
     *                this column." required="true" rtexprvalue="true"
     * 
     * @return Returns the identifier.
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void addTableAttributes(TableModel model, Table table) {
        table.addAttribute(TreeConstants.PARENT_ATTRIBUTE, TagUtils.evaluateExpressionAsString("parentAttribute", parentAttribute, pageContext));
        table.addAttribute(TreeConstants.IDENTIFIER, TagUtils.evaluateExpressionAsString("identifier", identifier, pageContext));
        
        table.setShowPagination(Boolean.FALSE);
        table.setRetrieveRowsCallback("org.extremecomponents.tree.ProcessTreeRowsCallback");
    }

    public void release() {
        parentAttribute = null;
        identifier = null;
        super.release();
    }
}
