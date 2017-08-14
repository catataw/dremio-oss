/*
 * Copyright (C) 2017 Dremio Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dremio.dac.cmd.upgrade.namespace_canonicalize_keys;


import com.dremio.dac.cmd.upgrade.UpgradeContext;
import com.dremio.service.namespace.NamespaceException;
import com.dremio.service.namespace.NamespaceService;
import com.dremio.service.namespace.dataset.proto.DatasetConfig;

/**
 * Represents a {@link DatasetConfig}
 */
class DatasetNode extends Node {
  private final DatasetConfig dataset;

  DatasetNode(DatasetConfig dataset) {
    super(NodeType.DATASET, dataset.getName(), dataset.getFullPathList());
    this.dataset = dataset;
  }

  @Override
  long getVersion() {
    return dataset.getVersion();
  }

  @Override
  void saveTo(NamespaceService ns, UpgradeContext context) throws NamespaceException {
    dataset.setName(getName());
    dataset.setFullPathList(getPath());
    ns.addOrUpdateDataset(toNamespaceKey(), dataset);
  }
}
