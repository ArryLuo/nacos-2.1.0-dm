/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.nacos.config.server.service.datasource;

import com.alibaba.nacos.config.server.utils.PropertyUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MockServletContext.class)
public class DynamicDataSourceTest {
    
    @InjectMocks
    private DynamicDataSource dataSource;
    
    @Mock
    private LocalDataSourceServiceImpl localDataSourceService;
    
    @Mock
    private ExternalDataSourceServiceImpl basicDataSourceService;
    
    @Before
    public void setUp() {
        dataSource = DynamicDataSource.getInstance();
        ReflectionTestUtils.setField(dataSource, "localDataSourceService", localDataSourceService);
        ReflectionTestUtils.setField(dataSource, "basicDataSourceService", basicDataSourceService);
    }
    
    @Test
    public void testGetDataSource() {
        
        Mockito.mockStatic(PropertyUtil.class);
        
        when(PropertyUtil.isEmbeddedStorage()).thenReturn(true);
        Assert.assertTrue(dataSource.getDataSource() instanceof LocalDataSourceServiceImpl);
        
        when(PropertyUtil.isEmbeddedStorage()).thenReturn(false);
        Assert.assertTrue(dataSource.getDataSource() instanceof ExternalDataSourceServiceImpl);
    }
    
}
