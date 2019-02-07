package contracts

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            description "should get all the gists"
            name "get_all_gists"
            request {
                method "GET"
                url "/gists"
                headers {
                    accept "application/json;charset=UTF-8"
                }
            }
            response {
                status 200
                body """
                        [
                          {
                            "id": "1",
                            "name": "Foo"
                          }
                        ]
                        """
                headers {
                    contentType "application/json;charset=UTF-8"
                }
            }
        },
        Contract.make {
            description "should get a gist"
            name "get_a_gist"
            request {
                method "GET"
                url "/gists/1"
                headers {
                    accept "application/json;charset=UTF-8"
                }
            }
            response {
                status 200
                body """
                          {
                            "id": "1",
                            "name": "Foo"
                          }
                        """
                headers {
                    contentType "application/json;charset=UTF-8"
                }
            }
        },
        Contract.make {
            description "should create a gist"
            name "create_a_gist"
            request {
                method "POST"
                url "/gists"
                headers {
                    accept "application/json;charset=UTF-8"
                    contentType "application/json;charset=UTF-8"
                }
                body """
                          {
                            "name": "Foo"
                          }
                        """
            }
            response {
                status 200
                body """
                          {
                            "id": "1",
                            "name": "Foo"
                          }
                        """
                headers {
                    contentType "application/json;charset=UTF-8"
                }
            }
        }
]