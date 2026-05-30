package pjatk.tpo.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data")
public class ToDoAppController {

    private final QuestsService service;

    public ToDoAppController(QuestsService service) {
        this.service = service;
    }

    @GetMapping
    List<Quests> getTasks(@RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "asc") String order) {
        return service.getQuests(sortBy, order);
    }

    @PostMapping
    ResponseEntity<Quests> addQuest(@RequestBody Quests quests) {
        Quests saved =  service.addQuest(quests);
        return new ResponseEntity<>(saved,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuest(@PathVariable Long id) {
        service.deleteQuestById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Quests> updateQuest(@PathVariable Long id, @RequestBody Quests quests) {
        try{
            Quests updatedQuest = service.updateQuest(id, quests);
            return new ResponseEntity<>(updatedQuest, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
