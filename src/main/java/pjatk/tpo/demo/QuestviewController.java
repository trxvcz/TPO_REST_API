package pjatk.tpo.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class QuestviewController {
    private final QuestsService questsService;
    public QuestviewController(QuestsService questsService) {
        this.questsService = questsService;
    }


    @GetMapping("/")
    public String ViewHomePage(Model model, @RequestParam(defaultValue = "id") String sortBy,@RequestParam(defaultValue = "asc") String direction){
        String sortDirection = direction.equals("asc") ? "desc" : "asc";

        model.addAttribute("questsList",questsService.getQuests(sortBy,sortDirection));
        model.addAttribute("sortDirection",sortDirection);
        model.addAttribute("quest",new Quests());

        return "index";
    }

    @PostMapping("/ui/save")
    public String saveQuest(@ModelAttribute Quests quests){
        questsService.addQuest(quests);
        return "redirect:/";
    }


    @GetMapping("/ui/edit/{id}")
    public String editQuest(@PathVariable Long id, Model model) {
        Quests quests = questsService.questsRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        model.addAttribute("quest", quests);
        model.addAttribute("questsList", questsService.getQuests("id", "asc"));
        model.addAttribute("reverseSortDir", "desc");

        return "index";
    }

    @GetMapping("/ui/delete/{id}")
    public String deleteQuest(@PathVariable Long id) {

        questsService.deleteQuestById(id);
        return "redirect:/";
    }
}
