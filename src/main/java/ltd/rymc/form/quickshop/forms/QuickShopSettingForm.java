package ltd.rymc.form.quickshop.forms;

import ltd.rymc.form.quickshop.form.RForm;
import ltd.rymc.form.quickshop.form.RSimpleForm;
import ltd.rymc.form.quickshop.shop.QuickShop;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.util.FormImage;

public class QuickShopSettingForm extends RSimpleForm {

    private final QuickShop shop;
    public QuickShopSettingForm(Player player, RForm previousForm, QuickShop shop) {
        super(player, previousForm);
        this.shop = shop;
        title("商店設定");
        content("商店設定");
        button("切換商店狀態", FormImage.of(FormImage.Type.PATH, "textures/ui/arrow_dark_right_stretch.png"));
        button("修改商店價格", FormImage.of(FormImage.Type.PATH, "textures/ui/store_home_icon.png"));
        button("§c刪除商店", FormImage.of(FormImage.Type.PATH, "textures/ui/realms_red_x.png"));
    }

    @Override
    @SuppressWarnings("ConstantValue")
    public void onValidResult(SimpleForm form, SimpleFormResponse response) {
        int id = response.clickedButtonId();
        if (id == 0) shop.changeType(bukkitPlayer);
        else if (id == 1) new QuickShopPriceSetForm(bukkitPlayer,this, shop).send();
        else if (id == 2) new QuickShopConfirmForm(bukkitPlayer,this, shop).send();
    }
}
