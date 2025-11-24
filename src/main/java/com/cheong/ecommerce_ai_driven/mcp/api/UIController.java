package com.cheong.ecommerce_ai_driven.mcp.api;

import com.cheong.ecommerce_ai_driven.common.data.Connection;
import com.cheong.ecommerce_ai_driven.mcp.resource.HtmlResourceProvider;
import com.cheong.ecommerce_ai_driven.speciality.service.SpecialityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping("/api/v1/ui")
public class UIController {

    private final HtmlResourceProvider htmlResourceProvider;

    private final SpecialityService specialityService;

    public UIController(HtmlResourceProvider htmlResourceProvider,
                        SpecialityService specialityService) {
        this.htmlResourceProvider = htmlResourceProvider;
        this.specialityService = specialityService;
    }


//    @GetMapping(value = "/{component}")
//    @ResponseBody
//    public Flux<McpSchema.ResourceContents> getUiComponent(@PathVariable String component) {
//        return Flux.fromIterable(htmlResourceProvider.getHtmlPage(component).contents());
//    }
//
    @GetMapping(value = "/index")
    public Mono<Rendering> index() {

        return specialityService.findAll(null, null, Integer.MAX_VALUE, null)
                .map(Connection::getEdges)
                .flatMap(edges -> Flux.fromIterable(edges).map(Connection.Edge::getNode).collectList())
                .flatMap(specialityDTOS -> {
                    AtomicInteger index = new AtomicInteger(0);
                    List<Integer> slideIndex = specialityDTOS.stream().map(specialityDTO -> index.getAndIncrement()).toList();
                    return Mono.just(Rendering.view("service-list")
                            .modelAttribute("services", specialityDTOS)
                            .modelAttribute("slides", slideIndex)
                            .build());
                });
    }

}
