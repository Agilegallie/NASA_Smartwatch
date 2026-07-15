/**
 * Shared navigation for Crew Command watch prototype.
 * Swipe cycle: Hub → Timeline → C&W → Comm → Timers → Hub
 */
(function (global) {
  const ROUTES = [
    { id: "hub", file: "hub.html", icon: "⌂", label: "Menu" },
    { id: "timeline", file: "timeline.html", icon: "📅", label: "Timeline" },
    { id: "cautions", file: "cautions.html", icon: "⚠", label: "C&W" },
    { id: "communication", file: "communication.html", icon: "📡", label: "Comm" },
    { id: "timers", file: "timers.html", icon: "⏱", label: "Timers" },
  ];

  function currentFile() {
    const parts = location.pathname.split("/");
    return parts[parts.length - 1] || "hub.html";
  }

  function currentIndex() {
    const file = currentFile();
    const idx = ROUTES.findIndex((r) => r.file === file);
    return idx >= 0 ? idx : 0;
  }

  function goTo(index) {
    const route = ROUTES[((index % ROUTES.length) + ROUTES.length) % ROUTES.length];
    if (route.file !== currentFile()) {
      location.href = route.file;
    }
  }

  function renderNav(activeId) {
    const nav = document.querySelector(".watch-nav");
    if (!nav) return;
    nav.innerHTML = "";
    ROUTES.forEach((route) => {
      const a = document.createElement("a");
      const classes = ["nav-dot"];
      if (route.id === activeId) classes.push("active");
      if (route.id === "hub") classes.push("nav-home");
      a.className = classes.join(" ");
      a.href = route.file;
      a.title = route.label;
      a.setAttribute("aria-label", route.label);
      a.textContent = route.icon;
      nav.appendChild(a);
    });
  }

  function bindSwipe(watchEl) {
    if (!watchEl) return;
    let startX = 0;
    let startY = 0;
    watchEl.addEventListener(
      "touchstart",
      (e) => {
        startX = e.touches[0].clientX;
        startY = e.touches[0].clientY;
      },
      { passive: true }
    );
    watchEl.addEventListener("touchend", (e) => {
      const dx = e.changedTouches[0].clientX - startX;
      const dy = e.changedTouches[0].clientY - startY;
      if (Math.abs(dx) < 50 || Math.abs(dx) < Math.abs(dy)) return;
      const i = currentIndex();
      goTo(dx < 0 ? i + 1 : i - 1);
    });
  }

  /** Keyboard arrows for desktop testing (← →) */
  function bindKeyboard() {
    document.addEventListener("keydown", (e) => {
      if (e.key === "ArrowRight") {
        e.preventDefault();
        goTo(currentIndex() + 1);
      }
      if (e.key === "ArrowLeft") {
        e.preventDefault();
        goTo(currentIndex() - 1);
      }
      if (e.key === "Escape" || e.key === "h" || e.key === "H") {
        e.preventDefault();
        location.href = "hub.html";
      }
    });
  }

  function init(activeId) {
    renderNav(activeId);
    bindSwipe(document.getElementById("watch"));
    bindKeyboard();
  }

  global.CrewNav = { ROUTES, init, goTo, currentIndex };
})(window);
