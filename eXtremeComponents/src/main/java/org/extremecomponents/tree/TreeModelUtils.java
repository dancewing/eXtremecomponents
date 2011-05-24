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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.extremecomponents.table.core.TableModel;

/**
 * org.extremecomponents.tree.model.BaseModelUtils.java -
 * 
 * @author phorn
 */
public final class TreeModelUtils {
    private static Log logger = LogFactory.getLog(TreeModelUtils.class);

    private TreeModelUtils() {
    }

    /**
     * Recursively loads the tree structure into the treeList attribute and sets
     * the treeList as the collection to use.
     * @param model  TableModel
     * @param rows java.util.Collection
     * @return
     * @throws Exception
     */
    public static List<TreeNode> loadTreeStructure(TableModel model, Collection rows) throws Exception {
        List<TreeNode> result = new ArrayList<TreeNode>();
        List search = new ArrayList();
        search.addAll(rows);

        for (Iterator iter = search.iterator(); iter.hasNext();) {
            Object bean = iter.next();

            String parentAttribute = model.getTableHandler().getTable().getAttributeAsString(TreeConstants.PARENT_ATTRIBUTE);
            Object parentId = BeanUtils.getProperty(bean, parentAttribute);
            if (parentId == null || StringUtils.isBlank(parentId + "")) {
                // Load up the top level parents
                TreeNode node = new TreeNode(bean, getBeanId(model, bean), 0);
                result.add(node);
                iter.remove();
                loadChildren(model, result, search, node, 0);
            }
        }

        return result;
    }

    public static void loadChildren(TableModel model, List displayList, Collection searchList, TreeNode node, int currentDepth) throws Exception {

        currentDepth++;

        List subList = new ArrayList();
        subList.addAll(searchList);

        Object id = node.getIdentifier();
        String key = getNodeKey(model, id);

        Map openNodes = (Map) model.getTableHandler().getTable().getAttribute(TreeConstants.OPEN_NODES);
        if (openNodes.get(key) != null) {
            node.setOpen(true);
        } else {
            node.setOpen(false);
        }

        for (Iterator iter = subList.iterator(); iter.hasNext();) {
            Object bean = iter.next();

            if (nodeIsBeanParent(model, node, bean)) {
                TreeNode childNode = new TreeNode(bean, getBeanId(model, bean), currentDepth);
                node.addChild(childNode);
                childNode.setParent(node);
                iter.remove();

                if (isOpen(model, node, true)) {
                    displayList.add(childNode);
                }

                loadChildren(model, displayList, subList, childNode, currentDepth); // Recurse
            }
        }
    }


    public static boolean nodeIsBeanParent(TableModel model, TreeNode node, Object bean) throws Exception {

        String parentAttribute = model.getTableHandler().getTable().getAttributeAsString(TreeConstants.PARENT_ATTRIBUTE);

        Object parent = PropertyUtils.getProperty(bean, parentAttribute);

        if (parent != null)
            logger.debug("parent instanceof " + parent.getClass().getName());

        if (parent == null || StringUtils.isBlank(parent + "")) {
            return false;
        }

        Object nodeId = node.getIdentifier();
        Object parentId = getBeanId(model, parent);

        if (node.getBean().equals(parent))
            return true;
        if (nodeId.equals(parentId))
            return true;

        return false;
    }

    public static Object getBeanId(TableModel model, Object bean) throws Exception {
        try {
            String identifier = model.getTableHandler().getTable().getAttributeAsString(TreeConstants.IDENTIFIER);

            return PropertyUtils.getProperty(bean, identifier);

        } catch (NoSuchMethodException e) {

            return bean;
        }
    }

    /**
     * Find out if the node and all parents are open.
     */
    public static boolean isOpen(TableModel model, TreeNode node, boolean filterControlled) {
        boolean filtered = model.getLimit().isFiltered();
        boolean cleared = model.getLimit().isCleared();

        if (filterControlled && filtered && !cleared) {
            node.setOpen(true);

            return true;
        }

        if (!node.isOpen()) {
            return false;
        }

        if (node.getParent() == null) {
            return true;
        }

        return isOpen(model, node.getParent(), filterControlled);
    }

    /**
     * @return Returns the parameter key used to indicate this node is open.
     */
    public static String getNodeKey(TableModel model, Object id) {
        return model.getTableHandler().prefixWithTableId() + TreeConstants.OPEN + id;
    }
}
