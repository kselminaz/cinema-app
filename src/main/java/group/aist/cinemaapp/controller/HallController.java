package group.aist.cinemaapp.controller;

import group.aist.cinemaapp.dto.response.HallResponse;
import group.aist.cinemaapp.service.HallService;
import group.aist.cinemaapp.service.impl.HallServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/halls")
@RequiredArgsConstructor
public class HallController {

    private final HallServiceImpl hallService;

//   @GetMapping("/all")
//    public List<HallResponse> getHalll{
//
//return hallService.getHall();
//}
}
