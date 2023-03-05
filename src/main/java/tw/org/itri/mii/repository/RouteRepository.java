package tw.org.itri.mii.repository;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.org.itri.mii.entity.Accordion;
import tw.org.itri.mii.entity.Route;

/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Repository
public interface RouteRepository extends JpaRepository<Route, Short>, JpaSpecificationExecutor<Route> {

	public long countByAccordion(@Param("accordion") Accordion accordion);

	public long countByAccordionAndLabel(@Param("accordion") Accordion accordion, @Param("label") String label);

	public long countByAccordionAndLabelAndIdNot(@Param("accordion") Accordion accordion, @Param("label") String label, @Param("id") Short id);

	public long countByHyperlink(@Param("hyperlink") String hyperlink);

	public long countByHyperlinkAndIdNot(@Param("hyperlink") String hyperlink, @Param("id") Short id);

	public Collection<Route> findByAccordionOrderByOrdinal(@Param("accordion") Accordion accordion);
}
