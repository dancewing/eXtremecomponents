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

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * org.extremecomponents.tree.bean.TreeNode.java -
 * 
 * @author Paul Horn
 */
public final class TreeNode extends HashMap {
    private static Log logger = LogFactory.getLog(TreeNode.class);
    private Object identifier;
    private Object bean;
    private TreeNode parent;
    private List children;
    private int depth;
    private boolean open;

    public TreeNode() {
        super();
    }

    public TreeNode(Object bean, Object identifier, int depth) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean instanceof Map) {
            this.putAll((Map) bean);
        } else {
            PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(bean.getClass());
            for (int i = 0; i < descriptors.length; i++) {
                this.put(descriptors[i].getName(), BeanUtils.getProperty(bean, descriptors[i].getName()));
            }
        }
        setBean(bean);
        this.identifier = identifier;
        this.depth = depth;
    }

    /**
     * @param child
     */
    public void addChild(Object child) {
        if (children == null) {
            children = new ArrayList();
        }

        children.add(child);
    }

    /**
     * @return Returns the bean.
     */
    public Object getBean() {
        return bean;
    }

    /**
     * @param bean
     *            The bean to set.
     */
    public void setBean(Object bean) {
        this.bean = bean;

        PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(bean);

        for (int i = 0; i < descriptors.length; i++) {
            try {
                String propertyName = descriptors[i].getDisplayName();
                Object val = BeanUtils.getProperty(bean, propertyName);
                this.put(propertyName, val);
            } catch (Exception e) {
                logger.error("TreeNode.setBean() Problem", e);
            }
        }
    }

    /**
     * @return Returns the children.
     */
    public List getChildren() {
        return children;
    }

    /**
     * @param children
     *            The children to set.
     */
    public void setChildren(List children) {
        this.children = children;
    }

    /**
     * @return Returns the parent.
     */
    public TreeNode getParent() {
        return parent;
    }

    /**
     * @param parent
     *            The parent to set.
     */
    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    /**
     * @return Returns the depth.
     */
    public int getDepth() {
        return depth;
    }

    /**
     * @param depth
     *            The depth to set.
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    /**
     * @return Returns the open.
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * @param open
     *            The open to set.
     */
    public void setOpen(boolean open) {
        this.open = open;
    }

    /**
     * @return
     */
    public Object getIdentifier() {
        return identifier;
    }

    /**
     * @param object
     */
    public void setIdentifier(Object object) {
        identifier = object;
    }

    public boolean equals(Object obj) {
        TreeNode node = (TreeNode) obj;

        return (super.equals(obj) || this.identifier.equals(node.getIdentifier()));
    }
}
