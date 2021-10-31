package com.project.pwc.controller;

import com.project.pwc.model.Route;
import com.project.pwc.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/routing")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping("/{origin}/{destination}")
    public ResponseEntity<Route> getRouteFromDestinations(@PathVariable("origin") String origin, @PathVariable("destination") String destination) {
        Route route = routeService.route(origin, destination);

        return ResponseEntity.ok(route);

        // todo: http 400, ve getRoutingWithDestinationV2 methodunda kullanılan message converter kısmının configteki bean içine alınması
    }

}
