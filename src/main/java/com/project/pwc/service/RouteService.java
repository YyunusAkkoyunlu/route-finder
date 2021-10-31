package com.project.pwc.service;

import com.project.pwc.exception.RouteNotFoundException;
import com.project.pwc.model.Country;
import com.project.pwc.model.Route;
import com.project.pwc.utility.MyCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RouteService {

    @Autowired
    RestTemplate restTemplate;


    public static Map<String, Country> mapCountry = new HashMap<>();

    @Value("${country.url}")
    private String countryURL;


    public Route route(String origin, String destination) {
        if (mapCountry == null || mapCountry.isEmpty()) {
            mapCountry = getRoutingWithDestination();
        }

        var originCountry = Optional.ofNullable(mapCountry.get(origin))
                .orElseThrow(() -> new RouteNotFoundException(String.format("Unknown origin country: %s", origin)));

        var destinationCountry = Optional.ofNullable(mapCountry.get(destination))
                .orElseThrow(() -> new RouteNotFoundException(String.format("Unknown destination country: %s", destination)));

        if (!origin.equals(destination)) {
            if (originCountry.getBorders() == null || originCountry.getBorders().isEmpty()) {
                throw new RouteNotFoundException(String.format("Origin %s has no border", origin));
            }

            if (destinationCountry.getBorders() == null || destinationCountry.getBorders().isEmpty()) {
                throw new RouteNotFoundException(String.format("Destination %s has no border", destination));
            }
        }

        List<String> routeList = routeFinder(mapCountry.get(origin), mapCountry.get(destination));

        Route route = new Route();
        route.setRoute(routeList);

        return route;
    }

    public Map<String, Country> getRoutingWithDestination() {
        List<Country> countries = Arrays.asList(restTemplate.getForObject(countryURL, Country[].class));

        return countries.stream()
                .collect(Collectors.toMap(Country::getCca3, c -> c));
    }

    public static List<String> routeFinder(Country origin, Country destination) {
        Queue<Country> queue = new LinkedList<>();
        Set<Country> visited = new HashSet<>();

        final Map<Country, Country> previous = new HashMap<>();

        Country currentCountry = origin;

        queue.add(currentCountry);
        visited.add(currentCountry);

        loop: while (!queue.isEmpty()) {
            currentCountry = queue.remove();
            if (currentCountry.equals(destination)) {
                break;
            } else {
                for (String border : currentCountry.getBorders()) { // Check each neighbor node
                    var neighbourCountry = mapCountry.get(border);
                    if (!visited.contains(neighbourCountry)) {
                        queue.add(neighbourCountry);
                        visited.add(neighbourCountry);
                        previous.put(neighbourCountry, currentCountry);
                        if (neighbourCountry.equals(destination)) {
                            currentCountry = neighbourCountry;
                            break loop;
                        }
                    }
                }
            }
        }

        List<Country> path = new ArrayList<>();
        for (Country node = destination; node != null; node = previous.get(node)) {
            path.add(node);
        }

        return path.stream()
                .map(Country::getCca3)
                .collect(MyCollector.reverse());
    }

}
