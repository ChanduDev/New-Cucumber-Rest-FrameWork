package dataSerialization;

import pojo.AddPlace;
import pojo.AddVotes;
import pojo.Location;

import java.util.ArrayList;
import java.util.List;

public class TestDataBuild {

    public AddVotes addNewVotesPayLoad(String image_id, String sub_id, int value) {

        AddVotes vote = new AddVotes();
        vote.setImage_id(image_id);
        vote.setSub_id(sub_id);
        vote.setValue(value);

        return vote;
    }

}
