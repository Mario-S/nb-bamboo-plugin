/* 
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
package org.netbeans.modules.bamboo.model.rcp;

import java.util.Optional;

/**
 *
 * @author Mario Schroeder
 */
final class AvailabilityVerifier {
    
    private AvailabilityVerifier() {
    }
    
    static boolean isAvailable(TraverseUp<? extends Availability> entity) {
       boolean available = false;
       Optional<? extends Availability> instance = entity.getParent();
       if(instance.isPresent()){
          available = instance.get().isAvailable();
       }
       return available;
    }
}
