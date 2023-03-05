package tw.org.itri.mii.repository;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tw.org.itri.mii.entity.Ally;

/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Repository
public interface AllyRepository extends JpaRepository<Ally, Short>, JpaSpecificationExecutor<Ally>, AllyRepositoryCustom {

	@Query(countName = "Ally.findDisplayable")
	public Collection<Ally> findDisplayable();
}
