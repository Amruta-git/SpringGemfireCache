package com.jp.spring;

import java.util.Date;

import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnableGemFireProperties;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.web.client.RestTemplate;

import com.jp.spring.model.Employee;
import com.jp.spring.repository.EmployeeRepository;

@SpringBootApplication
@ClientCacheApplication(name="SpringGemFireCache", logLevel = "error")
//@EnableEntityDefinedRegions(basePackageClasses = Employee.class, clientRegionShortcut = ClientRegionShortcut.LOCAL)
@EnableEntityDefinedRegions(basePackages = "com.jp.spring.model", clientRegionShortcut = ClientRegionShortcut.LOCAL)
@EnableGemfireRepositories(basePackages = "com.jp.spring.repository")
@EnableGemFireProperties
public class SpringGemFireCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringGemFireCacheApplication.class, args);
	}
	
	@Bean
	   public RestTemplate getRestTemplate() {
	      return new RestTemplate();
	   }
//	
//    @Bean
//    ApplicationRunner run(EmployeeRepository empRepo) {
//         
//        System.out.println("Spring Data GemFire example");
//        
//		Employee employee1 = new Employee(10010, new Date(1963,6,1), "Duangkaew", "Piveteau", "F", new Date(1989,8,24));
//		Employee employee2 = new Employee(10011, new Date(1953,11,7), "Mary", "Sluis", "F", new Date(1990,1,22));
//         
//
//         
//        // create
//		empRepo.save(employee1);
//		empRepo.save(employee2);
//         
//        // read
//		empRepo.findAll().forEach(blog -> System.out.println(blog));
//         
////        // find by title
////        System.out.println("Finding JCG...");
////        Blog temp = blogRepo.findByTitle("JCG");
////        System.out.println(temp);
//// 
////        // update
////        temp.setTitle("new JCG");
////        blogRepo.save(temp);
////        System.out.println("JCG updated...");
////        blogRepo.findAll().forEach(blog -> System.out.println(blog));
////         
////        // delete
////        System.out.println("Deleting Example");
////        temp = blogRepo.findByTitle("Example");
////        blogRepo.delete(temp);
////        blogRepo.findAll().forEach(blog -> System.out.println(blog));
//         
//        return null;
//    }

}
