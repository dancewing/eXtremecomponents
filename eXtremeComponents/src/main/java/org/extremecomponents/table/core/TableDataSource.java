// Copyright 2007, 2008 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.extremecomponents.table.core;

import org.extremecomponents.table.limit.Filter;
import org.extremecomponents.table.limit.Sort;

import java.util.Collection;
import java.util.List;


public interface TableDataSource
{

    int getAvailableRows(List<Filter> filter, Sort sort);

    Collection getData(int startIndex, int endIndex);

}
