package com.hkp.freetre.configure;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;


@OpenAPIDefinition(
		info=@Info(
				title="freetree",
				description="description for my app",
				termsOfService="t&c",
				version="v3"
				),
		servers= {
				@Server(
						description="url is userd for documentation",
						url="http://localhost:8080"
						)
		}
		)

public class SwaggerConfig {
	

}
