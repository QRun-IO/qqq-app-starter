/*
 * QQQ - Low-code Application Framework for Engineers.
 * Copyright (C) 2021-2022.  Kingsrook, LLC
 * 651 N Broad St Ste 205 # 6917 | Middletown DE 19709 | United States
 * contact@kingsrook.com
 * https://github.com/Kingsrook/
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.kingsrook.qqq.starterapp;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.atomic.AtomicReference;
import com.kingsrook.qqq.backend.core.context.QContext;
import io.javalin.Javalin;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/*******************************************************************************
 ** Verifies the new-user HTTP entry point and its registered table metadata.
 *******************************************************************************/
class StarterAppTest
{
   /*******************************************************************************
    **
    *******************************************************************************/
   @Test
   void testServer_servesDashboardAndSampleTable() throws Exception
   {
      StarterAppJavalinServer server = new StarterAppJavalinServer();
      AtomicReference<Javalin> service = new AtomicReference<>();
      server.setPort(0);
      server.withJavalinConfigurationCustomizer(service::set);

      try
      {
         server.start();
         URI baseUri = URI.create("http://localhost:" + service.get().port());
         try(HttpClient client = HttpClient.newHttpClient())
         {
            HttpResponse<String> dashboard = client.send(HttpRequest.newBuilder(baseUri.resolve("/")).build(), HttpResponse.BodyHandlers.ofString());
            assertEquals(200, dashboard.statusCode());
            assertTrue(dashboard.body().contains("<html"));

            HttpResponse<String> metadata = client.send(HttpRequest.newBuilder(baseUri.resolve("/metaData")).build(), HttpResponse.BodyHandlers.ofString());
            assertEquals(200, metadata.statusCode());
            assertTrue(metadata.body().contains("sampleTable"));
            assertTrue(metadata.body().contains("sampleApp"));
         }
      }
      finally
      {
         server.stop();
         QContext.clear();
      }
   }
}
