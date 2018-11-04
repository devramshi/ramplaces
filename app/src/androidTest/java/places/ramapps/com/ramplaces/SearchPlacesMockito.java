package places.ramapps.com.ramplaces;


import com.ramapps.ramplaces.app.data.PlacesApi;
import com.ramapps.ramplaces.app.data.response.Category;
import com.ramapps.ramplaces.app.data.response.Response;
import com.ramapps.ramplaces.app.data.response.SearchResponse;
import com.ramapps.ramplaces.app.data.response.Venue;
import com.ramapps.ramplaces.app.presenter.PlaceSearchPresenter;
import com.ramapps.ramplaces.app.repo.PlacesRepo;
import com.ramapps.ramplaces.app.ui.view.IHomeView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class SearchPlacesMockito {
    @Mock
    private PlacesApi placesSource;

    @Mock
    private PlacesRepo repo;

    @Mock
    private IHomeView view;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fetchValidLocationShouldLoadPlacesIntoView() {
        List<Venue> venueList = generateMockVenues();
        when(repo.findNearest(25d, 26d))
                .thenReturn(Observable.fromArray(venueList));

        PlaceSearchPresenter presenter = new PlaceSearchPresenter();
        presenter.setView(view);
        presenter.setRepo(repo);
        presenter.findNearestPlaces(25d, 26d);

        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view, times(1)).showProgress();
        inOrder.verify(view, times(1)).showPlaces(venueList);
    }


    private SearchResponse generateMockSearchResponse() {
        SearchResponse searchResponse = new SearchResponse();
        Response response = new Response();
        response.setVenues(generateMockVenues());
        searchResponse.setResponse(response);
        return searchResponse;
    }

    private List<Venue> generateMockVenues() {
        return Arrays.asList(new Venue("SMPLEid", "First Item", null, getMockCategories(), "REF"));
    }

    private List<Category> getMockCategories() {
        return Arrays.asList(new Category("sfsf", "ffdf", "Restaurent", null));
    }


}
