// Copyright 2000-2021 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.openapi.externalSystem.service.project;

import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.extensions.RequiredElement;
import com.intellij.openapi.externalSystem.model.ProjectSystemId;
import com.intellij.openapi.util.Pair;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.xmlb.annotations.Attribute;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;

/**
 * Specify if {@link ExternalSystemProjectResolver} for this {@link ProjectSystemId}
 * executes only trusted code and doesn't execute any untrusted (user-specified) code.
 *
 * By default every {@link ExternalSystemProjectResolver} is considered unsafe
 */
@ApiStatus.Experimental
public class ExternalResolverIsSafe {
  public static final ExtensionPointName<ExternalResolverIsSafe> EP_NAME =
    ExtensionPointName.create("com.intellij.externalResolverIsSafe");

  @Attribute("systemId")
  @RequiredElement
  public String systemId;

  @Attribute("executesTrustedCodeOnly")
  @RequiredElement
  public boolean executesTrustedCodeOnly;

  public static boolean executesTrustedCodeOnly(ProjectSystemId... systemIds) {
    Map<String, Boolean> executesTrustedCodeOnlyIndex =
      ContainerUtil.map2Map(EP_NAME.getExtensionList(), it -> new Pair<>(it.systemId, it.executesTrustedCodeOnly));
    return ContainerUtil.and(systemIds, it -> executesTrustedCodeOnlyIndex.getOrDefault(it.getId(), false));
  }
}
