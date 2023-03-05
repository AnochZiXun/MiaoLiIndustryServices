package tw.org.itri.mii.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.org.itri.mii.entity.Clan;
import tw.org.itri.mii.entity.Privilege;
import tw.org.itri.mii.entity.Route;

/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Short> {

	public long countByClan(@Param("clan") Clan clan);

	public Privilege findOneByRouteAndClan(@Param("route") Route route, @Param("clan") Clan clan);
}
